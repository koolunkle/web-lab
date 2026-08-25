package com.example

import com.example.plugins.configureOpenApi
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureStatusPages
import com.example.repositories.TodoRepository
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    val todoRepository = TodoRepository()
    configureSerialization()
    configureStatusPages()
    configureOpenApi()
    configureRouting(todoRepository)
}
