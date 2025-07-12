package com.humming.springmvc.plugin.simpleexcel.excelimport;

import java.lang.annotation.*;


/**
 * 标识在controller方法的参数上，用于标识excel导入的参数
 * @author Humming00
 * @date 2025/07/09
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelImportObjects {
}
