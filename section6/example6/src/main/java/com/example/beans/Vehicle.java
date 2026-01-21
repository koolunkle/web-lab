package com.example.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Vehicle {

    private String name;

    @Autowired
    private Engine engine;

    public Vehicle() {
        System.out.println("Vehicle bean created by Spring");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void sayHello() {
        System.out.println("Printing Hello from Component Vehicle Bean");
    }

    @Override
    public String toString() {
        return "Vehicle{"
                + "name='" + name + '\''
                + '}';
    }

    @PostConstruct
    public void initialize() {
        this.name = "Kia";
    }
}
