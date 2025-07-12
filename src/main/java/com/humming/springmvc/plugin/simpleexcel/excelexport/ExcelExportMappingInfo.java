package com.humming.springmvc.plugin.simpleexcel.excelexport;

import java.lang.reflect.Method;

/***
 * 记录Excel和接口映射关系
 */
public class ExcelExportMappingInfo {

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