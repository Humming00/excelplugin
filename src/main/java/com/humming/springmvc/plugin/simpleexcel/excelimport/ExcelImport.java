package com.humming.springmvc.plugin.simpleexcel.excelimport;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 标注在controller方法上，定义导出url的路径
 *
 * @author Humming00
 * @date 2025/07/09
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ExcelImport {
    String path() ;
}
