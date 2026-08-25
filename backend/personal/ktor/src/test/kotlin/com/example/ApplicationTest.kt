package com.example

import com.example.models.Todo
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureStatusPages
import com.example.repositories.TodoRepository
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {

    private fun Application.testModule() {
        configureSerialization()
        configureStatusPages()
        configureRouting(TodoRepository())
    }

    @Test
    fun `crud lifecycle works end to end`() = testApplication {
        application { testModule() }
        val client = createClient {
            install(ContentNegotiation) { json() }
        }

        val root = client.get("/")
        assertEquals(HttpStatusCode.OK, root.status)

        val created = client.post("/todos") {
            contentType(ContentType.Application.Json)
            setBody("""{"title":"Buy milk"}""")
        }
        assertEquals(HttpStatusCode.Created, created.status)
        val createdTodo = created.body<Todo>()
        assertEquals("Buy milk", createdTodo.title)
        assertEquals(false, createdTodo.completed)

        val list = client.get("/todos")
        assertEquals(HttpStatusCode.OK, list.status)
        val todos = list.body<List<Todo>>()
        assertTrue(todos.any { it.id == createdTodo.id })

        val updated = client.put("/todos/${createdTodo.id}") {
            contentType(ContentType.Application.Json)
            setBody("""{"title":"Buy milk","completed":true}""")
        }
        assertEquals(HttpStatusCode.OK, updated.status)
        assertEquals(true, updated.body<Todo>().completed)

        val deleted = client.delete("/todos/${createdTodo.id}")
        assertEquals(HttpStatusCode.NoContent, deleted.status)

        val getAfterDelete = client.get("/todos/${createdTodo.id}")
        assertEquals(HttpStatusCode.NotFound, getAfterDelete.status)
    }
}
