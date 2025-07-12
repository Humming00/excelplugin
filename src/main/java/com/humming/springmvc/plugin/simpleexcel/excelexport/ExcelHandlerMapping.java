package com.humming.springmvc.plugin.simpleexcel.excelexport;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

/**
 * HandlerMapping 的实现，为url映射到一个Handler
 */
public class ExcelHandlerMapping extends AbstractHandlerMethodMapping<ExcelExportMappingInfo> {

    private final static Comparator<ExcelExportMappingInfo> DirectThrowComparator = (o1, o2) -> {
        throw new UnsupportedOperationException("ExcelHandlerMappingNotSupportMultiExcelExportMappingInfo");
    };

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }


    @Override
    protected ExcelExportMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        ExcelExportMappingInfo excelMappingInfo = createExcelMappingInfo(method, handlerType);
        return excelMappingInfo;
    }

    @Override
    protected ExcelExportMappingInfo getMatchingMapping(ExcelExportMappingInfo info, HttpServletRequest request) {
        PathContainer path = ServletRequestPathUtils.getParsedRequestPath(request).pathWithinApplication();
        if (info.getPath().equals(path.value())) {
            return info;
        }
        return null;
    }

    @Override
    protected Comparator<ExcelExportMappingInfo> getMappingComparator(HttpServletRequest request) {
        return DirectThrowComparator;
    }

    private ExcelExportMappingInfo createExcelMappingInfo(Method method, Class<?> handlerType) {
        if (Objects.isNull(method)) {
            return null;
        }

        ExcelExport ann = AnnotatedElementUtils.findMergedAnnotation(method, ExcelExport.class);
        if (Objects.isNull(ann)) {
            return null;
        }
        Class<?> headClass = getHeadClass(method);
        return buildExcelMappingInfo(method, handlerType, headClass, ann);
    }

    private ExcelExportMappingInfo buildExcelMappingInfo(Method method, Class<?> handlerType, Class<?> headClass, ExcelExport requestMapping) {
        ExcelExportMappingInfo mappingInfo = new ExcelExportMappingInfo();
        mappingInfo.setPath(requestMapping.path());
        mappingInfo.setHandlerType(handlerType);
        mappingInfo.setMethod(method);
        return mappingInfo;
    }

    @Override
    protected ExcelHandlerMethod createHandlerMethod(Object handler, Method method) {
        ExcelHandlerMethod excelHandlerMethod;
        if (handler instanceof String beanName) {
            excelHandlerMethod = new ExcelHandlerMethod(beanName,
                    obtainApplicationContext().getAutowireCapableBeanFactory(),
                    obtainApplicationContext(),
                    method);
        } else {
            excelHandlerMethod = new ExcelHandlerMethod(handler, method);
        }

        ExcelExport export = AnnotatedElementUtils.findMergedAnnotation(method, ExcelExport.class);
        // 解析表头的类型
        Class<?> headClass = getHeadClass(method);
        ExcelExportInfo excelExportInfo = new ExcelExportInfo(export.fileName(), export.sheetName(), headClass);
        excelHandlerMethod.setExcelExportInfo(excelExportInfo);
        return excelHandlerMethod;
    }

    @Override
    protected Set<String> getDirectPaths(ExcelExportMappingInfo mapping) {
        return Collections.singleton(mapping.getPath());
    }


    private Class<?> getHeadClass(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
        }
        throw new UnsupportedOperationException("UnknowHeadClass:" + method.getClass().getName() + "method:" + method.getName());
    }
}