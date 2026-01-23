package com.example.beans;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// @Scope("singleton")
@Scope(BeanDefinition.SCOPE_SINGLETON) // Optional: Explicitly defining singleton scope
@Lazy
public class MyService {

    public MyService() {
        System.out.println("MyService created");
    }
}
