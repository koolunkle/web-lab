package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val id: Int = 0,
    val title: String,
    val completed: Boolean = false,
)
