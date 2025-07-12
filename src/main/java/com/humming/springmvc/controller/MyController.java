package com.humming.springmvc.controller;


import com.humming.springmvc.plugin.simpleexcel.excelexport.ExcelExport;
import com.humming.springmvc.plugin.simpleexcel.excelimport.ExcelImport;
import com.humming.springmvc.plugin.simpleexcel.excelimport.ExcelImportObjects;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Humming00
 * @date 2025/07/12
 */
@RequestMapping
@ResponseBody
@Controller
public class MyController {


    @PostMapping("/listUser")
    @ExcelExport(path = "/exportUser",fileName = "1.xls",sheetName = "1")
    public List<UserVO> listUser(){
        return doListUser();
    }

    @ExcelImport(path = "/importUser")
    @PostMapping("/addUser")
    @ResponseBody
    public List<UserVO> addUser(@ExcelImportObjects @RequestBody List<UserVO> users){
        return users;
    }


    private List<UserVO> doListUser(){
        ArrayList<UserVO> userVOS = new ArrayList<>();
        userVOS.add(new UserVO(1,"1"));
        userVOS.add(new UserVO(2,"2"));
        return userVOS;
    }
}
