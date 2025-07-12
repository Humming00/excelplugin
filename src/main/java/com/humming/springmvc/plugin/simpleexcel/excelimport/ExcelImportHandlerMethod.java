package com.humming.springmvc.plugin.simpleexcel.excelimport;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * @author Humming00
 * @date 2025/07/09
 */
public class ExcelImportHandlerMethod extends HandlerMethod {
    private BeanFactory beanFactory;

    public ExcelImportHandlerMethod(Object bean, Method method) {
        super(bean, method);
    }

    public ExcelImportHandlerMethod(String beanName, AutowireCapableBeanFactory beanFactory,  MessageSource messageSource, Method method) {
        super(beanName, beanFactory, messageSource, method);
        this.beanFactory=beanFactory;
    }

    public ExcelImportHandlerMethod(String beanName, BeanFactory beanFactory, MessageSource messageSource, Method method) {
        super(beanName, beanFactory, messageSource, method);
        this.beanFactory=beanFactory;
    }

    @Override
    public ExcelImportHandlerMethod createWithResolvedBean() {
        Object handler = this.getBean();
        if (handler instanceof String) {
            Assert.state(this.beanFactory != null, "Cannot resolve bean name without BeanFactory");
            Object handlerBean = this.beanFactory.getBean((String) handler);
            return new ExcelImportHandlerMethod(handlerBean, this.getMethod());
        }else {
            return this;
        }
    }
}
