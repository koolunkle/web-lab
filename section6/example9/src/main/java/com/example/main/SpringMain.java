package com.example.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.beans.MyService;
import com.example.config.ProjectConfig;

public class SpringMain {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        MyService service1 = context.getBean(MyService.class);
        MyService service2 = context.getBean(MyService.class);

        System.out.println(service1 == service2); // Comparing hash code
    }
}
