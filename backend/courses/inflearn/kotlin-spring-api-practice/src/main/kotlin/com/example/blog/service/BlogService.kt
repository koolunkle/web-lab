package com.example.blog.service

import com.example.blog.core.exception.ExternalApiException
import com.example.blog.dto.BlogDto
import com.example.blog.entity.Blog
import com.example.blog.repository.BlogRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.Duration

@Service
class BlogService(
    private val kakaoWebClient: WebClient,
    private val blogRepository: BlogRepository,
    private val blogSearchService: BlogSearchService
) {

    fun getBlog(blogDto: BlogDto): String {
        val result = kakaoWebClient
            .get()
            .uri {
                it.path("/v2/search/blog")
                    .queryParam("query", blogDto.query)
                    .queryParam("sort", blogDto.sort)
                    .queryParam("page", blogDto.page)
                    .queryParam("size", blogDto.size).build()
            }
            .retrieve()
            .onStatus({ it.isError }) { response ->
                response.bodyToMono<String>().map { body ->
                    ExternalApiException("Kakao API Error [${response.statusCode()}]: $body")
                }
            }
            .bodyToMono<String>()
            .timeout(Duration.ofSeconds(5))
            .block()
            ?: throw ExternalApiException("Empty response from Kakao API")

        blogSearchService.updateSearchCount(blogDto.query)

        return result
    }

    fun getBlogTop10Popular(): List<Blog> = blogRepository.findTop10ByOrderByCntDesc()
}
