package com.example.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.beans.Engine;
import com.example.beans.Vehicle;
import com.example.config.ProjectConfig;

public class SpringMain {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Vehicle vehicle = context.getBean(Vehicle.class);
        Engine engine = context.getBean(Engine.class);

        System.out.println("Engine name from Spring Context is: " + engine.getName());
        System.out.println("Vehicle name from Spring Context is: " + vehicle.getName());
        System.out.println("Engine that Vehicle own is: " + vehicle.getEngine());
    }
}
