package com.example.aop

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AopApplication {

    @Bean
    fun runner(test: Test): CommandLineRunner = CommandLineRunner {
        test.performSomeTask()
    }
}

fun main(args: Array<String>) {
    runApplication<AopApplication>(*args)
}
