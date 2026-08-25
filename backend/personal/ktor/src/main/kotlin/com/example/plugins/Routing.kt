package com.example.plugins

import com.example.repositories.TodoRepository
import com.example.routes.todoRoutes
import io.github.smiley4.ktoropenapi.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing

fun Application.configureRouting(repository: TodoRepository) {
    routing {
        get("/", {
            hidden = true
            description = "헬스체크"
            response {
                code(HttpStatusCode.OK) {
                    description = "서버가 정상 동작 중"
                    body<String>()
                }
            }
        }) {
            call.respondText("Ktor Todo API is running")
        }
        todoRoutes(repository)
    }
}
