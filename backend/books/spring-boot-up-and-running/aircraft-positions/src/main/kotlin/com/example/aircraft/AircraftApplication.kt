package com.example.aircraft

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@EnableCaching
@EnableScheduling
@SpringBootApplication
class AircraftApplication

fun main(args: Array<String>) {
    runApplication<AircraftApplication>(*args)
}
