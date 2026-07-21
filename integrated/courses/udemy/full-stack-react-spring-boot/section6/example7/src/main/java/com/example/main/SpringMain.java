package com.example.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.beans.Coffee;
import com.example.beans.CoffeeShop;
import com.example.config.ProjectConfig;

public class SpringMain {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        CoffeeShop shop = context.getBean(CoffeeShop.class);
        Coffee coffee = context.getBean(Coffee.class);

        System.out.println("Coffee from Spring Context is: " + shop.getCoffee().makeCoffee());
    }
}
