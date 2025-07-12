package com.humming.springmvc.plugin.simpleexcel.excelimport;

import com.alibaba.excel.EasyExcel;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Humming00
 * @date 2025/07/09
 */
public class ExcelImportHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(ExcelImportObjects.class)) {
            Class<?> parameterType = parameter.getParameterType();
            return parameterType.isAssignableFrom(List.class);
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (webRequest.getNativeRequest() instanceof HttpServletRequest) {
            HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
            ServletInputStream inputStream = nativeRequest.getInputStream();
            Type genericParameterType = parameter.getGenericParameterType();
            if (genericParameterType instanceof ParameterizedType) {
                Type actualTypeArgument = ((ParameterizedType) genericParameterType).getActualTypeArguments()[0];
                return EasyExcel.read(inputStream).head((Class<?>)actualTypeArgument).sheet().doReadSync();
            }else{
                throw new UnsupportedOperationException("UnknowParameterType:" + genericParameterType.getTypeName());
            }
        }
        return null;
    }


}
