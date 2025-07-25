package com.humming.springmvc;


import com.humming.springmvc.controller.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
        MyController bean = run.getBean(MyController.class);
        bean.listUser();
    }
}
