package com.humming.springmvc.plugin.simpleexcel.excelexport;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Collections;

public class ExcelHandlerAdapter extends RequestMappingHandlerAdapter {

    public static final String ExcelHandlerMethodReqAttributeKey = "CuzzExcelHandlerMethodReqAttributeKey";

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        super.setReturnValueHandlers(Collections.singletonList(new ExcelExportReturnHandler()));
    }

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return handlerMethod instanceof ExcelHandlerMethod ;
    }

    @Override
    protected ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        if (!(handlerMethod instanceof ExcelHandlerMethod)) {
            throw new IllegalStateException("ExcelHandlerAdapter only supports ExcelHandlerMethod");
        }

        request.setAttribute(ExcelHandlerMethodReqAttributeKey, ((ExcelHandlerMethod) handlerMethod).getExcelExportInfo());
        return super.handleInternal(request, response, handlerMethod);
    }




    @Override
    protected long getLastModifiedInternal(HttpServletRequest request, HandlerMethod handlerMethod) {
        return -1;
    }
}
