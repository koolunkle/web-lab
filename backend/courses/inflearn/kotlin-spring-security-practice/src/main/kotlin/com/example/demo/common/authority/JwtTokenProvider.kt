package com.example.demo.common.authority

import com.example.demo.common.dto.CustomUser
import com.example.demo.common.status.TokenValidationResult
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.util.*

// Access Token 만료: 30분
const val ACCESS_EXPIRATION_MS: Long = 1000L * 60 * 30

// Refresh Token 만료: 7일
const val REFRESH_EXPIRATION_MS: Long = 1000L * 60 * 60 * 24 * 7

@Component
class JwtTokenProvider {

    private val log = LoggerFactory.getLogger(javaClass)

    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    // Access Token + Refresh Token 발급
    fun createToken(authentication: Authentication): TokenInfo {
        val authorities: String = authentication
            .authorities
            .joinToString(",") { it.authority.orEmpty() }

        val now = Date()

        val accessToken = Jwts.builder()
            .subject(authentication.name)
            .claim("auth", authorities)
            .claim("userId", (authentication.principal as CustomUser).userId)
            .issuedAt(now)
            .expiration(Date(now.time + ACCESS_EXPIRATION_MS))
            .signWith(key, Jwts.SIG.HS256)
            .compact()

        val refreshToken = Jwts.builder()
            .subject(authentication.name)
            .claim("userId", (authentication.principal as CustomUser).userId)
            .issuedAt(now)
            .expiration(Date(now.time + REFRESH_EXPIRATION_MS))
            .signWith(key, Jwts.SIG.HS256)
            .compact()

        return TokenInfo("Bearer", accessToken, refreshToken)
    }

    // Token claims → Authentication 복원
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token)
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val userId = claims["userId"] ?: throw RuntimeException("잘못된 토큰입니다.")

        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails =
            CustomUser(userId.toString().toLong(), claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    // Refresh Token에서 userId 추출
    fun getUserIdFromToken(token: String): Long {
        val claims: Claims = getClaims(token)
        return (claims["userId"] ?: throw RuntimeException("잘못된 토큰입니다."))
            .toString().toLong()
    }

    // 토큰 검증: VALID / EXPIRED / INVALID 반환
    fun validateToken(token: String): TokenValidationResult {
        return try {
            getClaims(token)
            TokenValidationResult.VALID
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT: {}", e.message)
            TokenValidationResult.EXPIRED
        } catch (e: SecurityException) {
            log.warn("유효하지 않은 JWT 서명: {}", e.message)
            TokenValidationResult.INVALID
        } catch (e: MalformedJwtException) {
            log.warn("잘못된 JWT 형식: {}", e.message)
            TokenValidationResult.INVALID
        } catch (e: UnsupportedJwtException) {
            log.warn("지원하지 않는 JWT: {}", e.message)
            TokenValidationResult.INVALID
        } catch (e: IllegalArgumentException) {
            log.warn("빈 JWT 클레임: {}", e.message)
            TokenValidationResult.INVALID
        }
    }

    // Token 원문 → SHA-256 해시 (DB 저장용)
    fun hashToken(token: String): String =
        Base64.getEncoder().encodeToString(
            MessageDigest.getInstance("SHA-256").digest(token.toByteArray(Charsets.UTF_8))
        )

    private fun getClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}
