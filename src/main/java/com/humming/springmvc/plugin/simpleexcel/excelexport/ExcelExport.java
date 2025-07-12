package com.humming.springmvc.plugin.simpleexcel.excelexport;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 标注在controller方法上，定义导出url的路径，文件名称和sheet名称
 *
 * @author Humming00
 * @date 2025/07/12
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ExcelExport {

    String path();

    String fileName();

    String sheetName();

}