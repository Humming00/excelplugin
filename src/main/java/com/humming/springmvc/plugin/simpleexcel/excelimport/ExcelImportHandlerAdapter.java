package com.humming.springmvc.plugin.simpleexcel.excelimport;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Collections;

/**
 * @author Humming00
 * @date 2025/07/09
 */
public class ExcelImportHandlerAdapter extends RequestMappingHandlerAdapter {
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        super.setArgumentResolvers(Collections.singletonList(new ExcelImportHandlerMethodArgumentResolver()));
    }

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return handlerMethod instanceof ExcelImportHandlerMethod;
    }
}
