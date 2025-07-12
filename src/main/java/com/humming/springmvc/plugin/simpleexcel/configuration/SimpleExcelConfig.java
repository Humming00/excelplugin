package com.humming.springmvc.plugin.simpleexcel.configuration;

import com.humming.springmvc.plugin.simpleexcel.excelexport.ExcelExportReturnHandler;
import com.humming.springmvc.plugin.simpleexcel.excelexport.ExcelHandlerAdapter;
import com.humming.springmvc.plugin.simpleexcel.excelexport.ExcelHandlerMapping;
import com.humming.springmvc.plugin.simpleexcel.excelimport.ExcelImportHandlerAdapter;
import com.humming.springmvc.plugin.simpleexcel.excelimport.ExcelImportHandlerMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.http.converter.*;
import org.springframework.http.converter.cbor.KotlinSerializationCborHttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.*;
import org.springframework.http.converter.protobuf.KotlinSerializationProtobufHttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SimpleExcelConfig implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private static final boolean romePresent;

    private static final boolean jaxb2Present;

    private static final boolean jackson2Present;

    private static final boolean jackson2XmlPresent;

    private static final boolean jackson2SmilePresent;

    private static final boolean jackson2CborPresent;

    private static final boolean gsonPresent;

    private static final boolean jsonbPresent;

    private static final boolean kotlinSerializationCborPresent;

    private static final boolean kotlinSerializationJsonPresent;

    private static final boolean kotlinSerializationProtobufPresent;

    private List<HttpMessageConverter<?>> messageConverters;


    static {
        ClassLoader classLoader = SimpleExcelConfig.class.getClassLoader();
        romePresent = ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", classLoader);
        jaxb2Present = ClassUtils.isPresent("jakarta.xml.bind.Binder", classLoader);
        jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader) &&
                ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
        jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader);
        jackson2SmilePresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.smile.SmileFactory", classLoader);
        jackson2CborPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.cbor.CBORFactory", classLoader);
        gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", classLoader);
        jsonbPresent = ClassUtils.isPresent("jakarta.json.bind.Jsonb", classLoader);
        kotlinSerializationCborPresent = ClassUtils.isPresent("kotlinx.serialization.cbor.Cbor", classLoader);
        kotlinSerializationJsonPresent = ClassUtils.isPresent("kotlinx.serialization.json.Json", classLoader);
        kotlinSerializationProtobufPresent = ClassUtils.isPresent("kotlinx.serialization.protobuf.ProtoBuf", classLoader);
    }


    public  void initHttpMessageConverters() {
        this.messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new ResourceRegionHttpMessageConverter());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());

        if (romePresent) {
            messageConverters.add(new AtomFeedHttpMessageConverter());
            messageConverters.add(new RssChannelHttpMessageConverter());
        }

        if (jackson2XmlPresent) {
            Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }
            messageConverters.add(new MappingJackson2XmlHttpMessageConverter(builder.build()));
        }
        else if (jaxb2Present) {
            messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        }

        if (kotlinSerializationCborPresent) {
            messageConverters.add(new KotlinSerializationCborHttpMessageConverter());
        }
        if (kotlinSerializationJsonPresent) {
            messageConverters.add(new KotlinSerializationJsonHttpMessageConverter());
        }
        if (kotlinSerializationProtobufPresent) {
            messageConverters.add(new KotlinSerializationProtobufHttpMessageConverter());
        }

        if (jackson2Present) {
            Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }
            messageConverters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        }
        else if (gsonPresent) {
            messageConverters.add(new GsonHttpMessageConverter());
        }
        else if (jsonbPresent) {
            messageConverters.add(new JsonbHttpMessageConverter());
        }

        if (jackson2SmilePresent) {
            Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.smile();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }
            messageConverters.add(new MappingJackson2SmileHttpMessageConverter(builder.build()));
        }
        if (jackson2CborPresent) {
            Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.cbor();
            if (this.applicationContext != null) {
                builder.applicationContext(this.applicationContext);
            }
            messageConverters.add(new MappingJackson2CborHttpMessageConverter(builder.build()));
        }
    }


    @Bean
    public ExcelHandlerMapping excelHandlerMapping() {
        ExcelHandlerMapping excelHandlerMapping = new ExcelHandlerMapping();
        excelHandlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return excelHandlerMapping;
    }
    @Bean
    public ExcelExportReturnHandler excelExportReturnHandler(){
        return new ExcelExportReturnHandler();
    }

    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    public ExcelHandlerAdapter excelHandlerAdapter() {
        return new ExcelHandlerAdapter();
    }

    @Bean
    public ExcelImportHandlerMapping excelImportHandlerMapping() {
        ExcelImportHandlerMapping excelImportHandlerMapping = new ExcelImportHandlerMapping();
        excelImportHandlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return excelImportHandlerMapping;
    }

    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    public ExcelImportHandlerAdapter excelImportHandlerAdapter() {
        ExcelImportHandlerAdapter excelImportHandlerAdapter = new ExcelImportHandlerAdapter();
        excelImportHandlerAdapter.setMessageConverters(this.messageConverters);
        return excelImportHandlerAdapter;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initHttpMessageConverters();
    }
}
