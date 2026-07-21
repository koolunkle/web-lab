package com.example.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Engine {

    private final Vehicle vehicle;

    @Autowired
    public Engine(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
