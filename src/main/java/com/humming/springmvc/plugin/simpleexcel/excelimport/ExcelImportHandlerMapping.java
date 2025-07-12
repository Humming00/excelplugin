package com.humming.springmvc.plugin.simpleexcel.excelimport;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

/**
 * @author Humming00
 * @date 2025/07/09
 */
public class ExcelImportHandlerMapping extends AbstractHandlerMethodMapping<ExcelImportMappingInfo> {

    private final static Comparator<ExcelImportMappingInfo> DirectThrowComparator = (o1, o2) -> {
        throw new UnsupportedOperationException("ExcelHandlerMappingNotSupportMultiExcelExportMappingInfo");
    };

    @Override
    protected boolean isHandler(Class beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    @Override
    protected ExcelImportMappingInfo getMappingForMethod(Method method, Class handlerType) {
        ExcelImportMappingInfo excelMappingInfo = createExcelMappingInfo(method, handlerType);
        return excelMappingInfo;
    }

    private ExcelImportMappingInfo createExcelMappingInfo(Method method, Class<?> handlerType) {
        if (Objects.isNull(method)) {
            return null;
        }

        ExcelImport ann = AnnotatedElementUtils.findMergedAnnotation(method, ExcelImport.class);
        if (Objects.isNull(ann)) {
            return null;
        }
        return buildExcelMappingInfo(method, handlerType, ann);
    }

    private ExcelImportMappingInfo buildExcelMappingInfo(Method method, Class<?> handlerType, ExcelImport requestMapping) {
        ExcelImportMappingInfo mappingInfo = new ExcelImportMappingInfo();
        mappingInfo.setPath(requestMapping.path());
        mappingInfo.setHandlerType(handlerType);
        mappingInfo.setMethod(method);
        return mappingInfo;
    }


    @Override
    protected ExcelImportMappingInfo getMatchingMapping(ExcelImportMappingInfo mapping, HttpServletRequest request) {
        PathContainer path = ServletRequestPathUtils.getParsedRequestPath(request).pathWithinApplication();
        if (mapping.getPath().equals(path.value())) {
            return mapping;
        }
        return null;
    }

    @Override
    protected Comparator getMappingComparator(HttpServletRequest request) {
        return DirectThrowComparator;
    }

    @Override
    protected ExcelImportHandlerMethod createHandlerMethod(Object handler, Method method) {
        ExcelImportHandlerMethod excelHandlerMethod;
        if (handler instanceof String beanName) {
            return new ExcelImportHandlerMethod(beanName,
                    obtainApplicationContext().getAutowireCapableBeanFactory(),
                    obtainApplicationContext(),
                    method);
        } else {
            excelHandlerMethod = new ExcelImportHandlerMethod(handler, method);
        }

        return excelHandlerMethod;
    }

    @Override
    protected Set<String> getDirectPaths(ExcelImportMappingInfo mapping) {
        return Collections.singleton(mapping.getPath());
    }



}
