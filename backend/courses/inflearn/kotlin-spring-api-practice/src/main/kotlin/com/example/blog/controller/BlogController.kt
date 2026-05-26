package com.example.blog.controller

import com.example.blog.dto.BlogDto
import com.example.blog.entity.Blog
import com.example.blog.service.BlogService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/blog")
class BlogController(private val blogService: BlogService) {

    @GetMapping
    fun getBlog(@Valid blogDto: BlogDto): String = blogService.getBlog(blogDto)

    @GetMapping("/rank")
    fun getBlogTop10Popular(): List<Blog> = blogService.getBlogTop10Popular()
}
