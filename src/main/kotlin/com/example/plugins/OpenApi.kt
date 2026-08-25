package com.example.plugins

import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.openApi
import io.github.smiley4.ktorswaggerui.swaggerUI
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureOpenApi() {
    install(OpenApi) {
        info {
            title = "Ktor Todo API"
            version = "0.0.1"
            description = "메모리 저장 기반 Todo CRUD 예제 API"
        }
    }
    routing {
        route("openapi.json") {
            openApi()
        }
        route("swagger") {
            swaggerUI("/openapi.json")
        }
    }
}
