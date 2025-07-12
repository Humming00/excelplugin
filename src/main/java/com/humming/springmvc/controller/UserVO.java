package com.humming.springmvc.controller;

public class UserVO {
    private int num;
    private String name;

    public UserVO(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public UserVO() {}

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
