package com.humming.springmvc.plugin.simpleexcel.excelexport;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

public class ExcelHandlerMethod  extends HandlerMethod {


    private ExcelExportInfo excelExportInfo;
    private BeanFactory beanFactory;

    public ExcelHandlerMethod(Object bean, Method method) {
        super(bean, method);
    }

    @Override
    public ExcelHandlerMethod createWithResolvedBean() {
        Object handler = this.getBean();
        if (handler instanceof String) {
            Assert.state(this.beanFactory != null, "Cannot resolve bean name without BeanFactory");
            Object handlerBean = this.beanFactory.getBean((String) handler);
            return buildWithResolvedBean(this, handlerBean, (String) handler);
        }else {
            return this;
        }
    }

    public ExcelHandlerMethod buildWithResolvedBean(ExcelHandlerMethod source,Object bean,String beanName) {
        ExcelHandlerMethod excelHandlerMethod = new ExcelHandlerMethod(bean, source.getMethod());
        excelHandlerMethod.setExcelExportInfo(source.getExcelExportInfo());
        return excelHandlerMethod;
    }


    protected ExcelHandlerMethod(Object bean, Method method, MessageSource messageSource) {
        super(bean, method, messageSource);
    }

    public ExcelHandlerMethod(Object bean, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        super(bean, methodName, parameterTypes);
    }

    public ExcelHandlerMethod(String beanName, BeanFactory beanFactory, Method method) {
        super(beanName, beanFactory, method);
        this.beanFactory=beanFactory;
    }

    public ExcelHandlerMethod(String beanName, BeanFactory beanFactory, MessageSource messageSource, Method method) {
        super(beanName, beanFactory, messageSource, method);
        this.beanFactory=beanFactory;
    }

    protected ExcelHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    public void setExcelExportInfo(ExcelExportInfo excelExportInfo) {
        this.excelExportInfo = excelExportInfo;
    }

    public ExcelExportInfo getExcelExportInfo() {
        return excelExportInfo;
    }
}
