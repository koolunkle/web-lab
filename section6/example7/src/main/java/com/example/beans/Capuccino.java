package com.example.beans;

import org.springframework.stereotype.Component;

@Component("capuccino")
public class Capuccino implements Coffee {

    @Override
    public String makeCoffee() {
        return "Capuccino Coffee";
    }
}
