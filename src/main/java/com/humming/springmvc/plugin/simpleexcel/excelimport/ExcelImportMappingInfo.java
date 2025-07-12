package com.humming.springmvc.plugin.simpleexcel.excelimport;

import java.lang.reflect.Method;

/**
 * @author Humming00
 * @date 2025/07/09
 */
public class ExcelImportMappingInfo {
    private String path;
    private Method method;
    private Class<?> handlerType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(Class<?> handlerType) {
        this.handlerType = handlerType;
    }
}
