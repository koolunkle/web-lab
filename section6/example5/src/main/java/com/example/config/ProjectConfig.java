package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.beans.Person;
import com.example.beans.Vehicle;

@Configuration
public class ProjectConfig {

    @Bean
    Vehicle vehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Toyota");
        return vehicle;
    }

    /*
     * @Bean
     * Person person() {
     * Person person = new Person();
     * person.setName("Lucy");
     * person.setVehicle(vehicle()); // Using method invocation
     * return person;
     * }
     */

    @Bean
    Person person(Vehicle vehicle) { // Using method parameters
        Person person = new Person();
        person.setName("Lucy");
        person.setVehicle(vehicle);
        return person;
    }
}
