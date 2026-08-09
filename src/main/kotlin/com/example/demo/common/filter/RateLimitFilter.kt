package com.example.demo.common.filter

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

class RateLimitFilter : OncePerRequestFilter() {

    // IP별 버킷 캐시 (인메모리, 애플리케이션 재시작 시 초기화)
    private val loginBuckets = ConcurrentHashMap<String, Bucket>()
    private val refreshBuckets = ConcurrentHashMap<String, Bucket>()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val path = request.requestURI
        val ip = resolveClientIp(request)

        val bucket = when {
            path == "/api/member/login" -> loginBuckets.computeIfAbsent(ip) { newLoginBucket() }
            path == "/api/member/refresh" -> refreshBuckets.computeIfAbsent(ip) { newRefreshBucket() }
            else -> null
        }

        if (bucket != null && !bucket.tryConsume(1)) {
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"
            response.writer.write(
                """{"resultCode":"ERROR","data":null,"message":"요청이 너무 많습니다. 잠시 후 다시 시도해주세요."}"""
            )
            return
        }

        filterChain.doFilter(request, response)
    }

    // /login: 분당 5회
    private fun newLoginBucket(): Bucket =
        Bucket.builder()
            .addLimit(Bandwidth.builder().capacity(5).refillGreedy(5, Duration.ofMinutes(1)).build())
            .build()

    // /refresh: 분당 10회
    private fun newRefreshBucket(): Bucket =
        Bucket.builder()
            .addLimit(Bandwidth.builder().capacity(10).refillGreedy(10, Duration.ofMinutes(1)).build())
            .build()

    // X-Forwarded-For 헤더 우선, 없으면 remoteAddr 사용
    private fun resolveClientIp(request: HttpServletRequest): String =
        request.getHeader("X-Forwarded-For")?.split(",")?.firstOrNull()?.trim()
            ?: request.remoteAddr
}
