package com.humming.springmvc.plugin.simpleexcel.excelexport;

public class ExcelExportInfo {

    private final String fileName;
    private final String sheetName;

    private final Class<?>headClass;
    public ExcelExportInfo(String fileName, String sheetName, Class<?> headClass) {
        this.fileName = fileName;
        this.sheetName = sheetName;
        this.headClass = headClass;
    }

    public String getSheetName() {
        return sheetName;
    }

    public String getFileName() {
        return fileName;
    }

    public Class<?> getHeadClass() {
        return headClass;
    }
}
