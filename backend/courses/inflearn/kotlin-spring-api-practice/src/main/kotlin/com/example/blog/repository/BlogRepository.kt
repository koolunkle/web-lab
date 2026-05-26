package com.example.blog.repository

import com.example.blog.entity.Blog
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface BlogRepository : CrudRepository<Blog, String> {

    fun findTop10ByOrderByCntDesc(): List<Blog>

    @Modifying
    @Query("update Blog b set b.cnt = b.cnt + 1 where b.word = :word")
    fun updateCntByWord(word: String): Int
}
