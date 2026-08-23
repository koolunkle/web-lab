package com.example.demo.common.authority

import com.example.demo.common.dto.CustomUser
import com.example.demo.common.status.TokenValidationResult
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

class JwtTokenProviderTest {

    private lateinit var provider: JwtTokenProvider

    // 테스트용 256비트 Base64 secret (32바이트)
    private val testSecret = "dGVzdFNlY3JldEtleUZvckp3dEF1dGhUZXN0aW5nMTIzNDU2Nzg="

    @BeforeEach
    fun setUp() {
        provider = JwtTokenProvider()
        provider.secretKey = testSecret
    }

    private fun makeAuthentication(userId: Long = 1L, loginId: String = "testuser"): UsernamePasswordAuthenticationToken {
        val authorities = listOf(SimpleGrantedAuthority("ROLE_MEMBER"))
        val principal = CustomUser(userId, loginId, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    @Test
    fun `토큰 발급 시 accessToken과 refreshToken이 모두 생성된다`() {
        val auth = makeAuthentication()
        val tokenInfo = provider.createToken(auth)

        assertEquals("Bearer", tokenInfo.grantType)
        assertTrue(tokenInfo.accessToken.isNotBlank())
        assertTrue(tokenInfo.refreshToken.isNotBlank())
    }

    @Test
    fun `유효한 accessToken은 VALID를 반환한다`() {
        val tokenInfo = provider.createToken(makeAuthentication())
        assertEquals(TokenValidationResult.VALID, provider.validateToken(tokenInfo.accessToken))
    }

    @Test
    fun `유효한 refreshToken에서 userId를 추출할 수 있다`() {
        val tokenInfo = provider.createToken(makeAuthentication(userId = 42L))
        assertEquals(42L, provider.getUserIdFromToken(tokenInfo.refreshToken))
    }

    @Test
    fun `잘못된 형식의 토큰은 INVALID를 반환한다`() {
        assertEquals(TokenValidationResult.INVALID, provider.validateToken("invalid.token.value"))
    }

    @Test
    fun `hashToken은 동일 입력에 동일 해시를 반환한다`() {
        val token = "some-refresh-token"
        assertEquals(provider.hashToken(token), provider.hashToken(token))
    }

    @Test
    fun `hashToken은 서로 다른 입력에 다른 해시를 반환한다`() {
        assertNotEquals(provider.hashToken("token-a"), provider.hashToken("token-b"))
    }

    @Test
    fun `accessToken에서 Authentication을 복원하면 loginId와 권한이 일치한다`() {
        val tokenInfo = provider.createToken(makeAuthentication(loginId = "testuser"))
        val authentication = provider.getAuthentication(tokenInfo.accessToken)

        assertEquals("testuser", authentication.name)
        assertTrue(authentication.authorities.any { it.authority == "ROLE_MEMBER" })
    }
}
