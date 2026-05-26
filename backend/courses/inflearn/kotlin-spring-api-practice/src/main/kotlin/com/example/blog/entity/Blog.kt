package com.example.blog.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Blog(
    @Id var word: String,
    var cnt: Int = 0
)
