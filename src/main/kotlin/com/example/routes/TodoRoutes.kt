package com.example.routes

import com.example.models.Todo
import com.example.repositories.TodoRepository
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.put
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.todoRoutes(repository: TodoRepository) {
    route("/todos") {
        get({
            description = "모든 Todo 목록을 조회합니다."
            response {
                code(HttpStatusCode.OK) {
                    description = "Todo 목록"
                    body<List<Todo>>()
                }
            }
        }) {
            call.respond(repository.getAll())
        }

        get("/{id}", {
            description = "id로 단건 Todo를 조회합니다."
            request {
                pathParameter<Int>("id") {
                    description = "Todo id"
                }
            }
            response {
                code(HttpStatusCode.OK) {
                    description = "조회된 Todo"
                    body<Todo>()
                }
                code(HttpStatusCode.NotFound) {
                    description = "Todo를 찾을 수 없음"
                }
            }
        }) {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid id")
            val todo = repository.getById(id)
                ?: throw NoSuchElementException("Todo $id not found")
            call.respond(todo)
        }

        post({
            description = "새 Todo를 생성합니다."
            request {
                body<Todo> {
                    description = "생성할 Todo (title 필수)"
                }
            }
            response {
                code(HttpStatusCode.Created) {
                    description = "생성된 Todo"
                    body<Todo>()
                }
                code(HttpStatusCode.BadRequest) {
                    description = "잘못된 요청 (title 누락 등)"
                }
            }
        }) {
            val request = call.receive<Todo>()
            if (request.title.isBlank()) throw IllegalArgumentException("title must not be blank")
            val created = repository.create(request.title, request.completed)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}", {
            description = "기존 Todo를 수정합니다."
            request {
                pathParameter<Int>("id") {
                    description = "Todo id"
                }
                body<Todo> {
                    description = "수정할 내용"
                }
            }
            response {
                code(HttpStatusCode.OK) {
                    description = "수정된 Todo"
                    body<Todo>()
                }
                code(HttpStatusCode.NotFound) {
                    description = "Todo를 찾을 수 없음"
                }
            }
        }) {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid id")
            val request = call.receive<Todo>()
            if (request.title.isBlank()) throw IllegalArgumentException("title must not be blank")
            val updated = repository.update(id, request.title, request.completed)
                ?: throw NoSuchElementException("Todo $id not found")
            call.respond(updated)
        }

        delete("/{id}", {
            description = "Todo를 삭제합니다."
            request {
                pathParameter<Int>("id") {
                    description = "Todo id"
                }
            }
            response {
                code(HttpStatusCode.NoContent) {
                    description = "삭제 성공"
                }
                code(HttpStatusCode.NotFound) {
                    description = "Todo를 찾을 수 없음"
                }
            }
        }) {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid id")
            if (!repository.delete(id)) throw NoSuchElementException("Todo $id not found")
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
