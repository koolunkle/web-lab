package com.example.repositories

import com.example.models.Todo
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class TodoRepository {
    private val todos = ConcurrentHashMap<Int, Todo>()
    private val nextId = AtomicInteger(1)

    fun getAll(): List<Todo> = todos.values.sortedBy { it.id }

    fun getById(id: Int): Todo? = todos[id]

    fun create(title: String, completed: Boolean): Todo {
        val id = nextId.getAndIncrement()
        val todo = Todo(id = id, title = title, completed = completed)
        todos[id] = todo
        return todo
    }

    fun update(id: Int, title: String, completed: Boolean): Todo? {
        if (!todos.containsKey(id)) return null
        val updated = Todo(id = id, title = title, completed = completed)
        todos[id] = updated
        return updated
    }

    fun delete(id: Int): Boolean = todos.remove(id) != null
}
