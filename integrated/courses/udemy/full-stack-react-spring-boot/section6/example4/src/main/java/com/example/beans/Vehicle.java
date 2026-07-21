package com.example.beans;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Vehicle {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("Printing Hello from Component Vehicle Bean");
    }

    @PostConstruct
    public void initialize() {
        this.name = "Honda";
    }
}
