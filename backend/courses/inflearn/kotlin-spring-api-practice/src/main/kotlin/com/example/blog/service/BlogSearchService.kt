package com.example.blog.service

import com.example.blog.entity.Blog
import com.example.blog.repository.BlogRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class BlogSearchService(private val blogRepository: BlogRepository) {

    @Transactional
    fun updateSearchCount(query: String) {
        val lowQuery = query.lowercase()
        val updatedCnt = blogRepository.updateCntByWord(lowQuery)

        if (updatedCnt == 0) {
            blogRepository.save(Blog(lowQuery, 1))
        }
    }
}
