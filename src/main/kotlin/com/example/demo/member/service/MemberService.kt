package com.example.demo.member.service

import com.example.demo.common.authority.JwtTokenProvider
import com.example.demo.common.authority.TokenInfo
import com.example.demo.common.dto.CustomUser
import com.example.demo.common.exception.InvalidInputException
import com.example.demo.common.status.Role
import com.example.demo.common.status.TokenValidationResult
import com.example.demo.member.dto.LoginDto
import com.example.demo.member.dto.MemberDtoRequest
import com.example.demo.member.dto.MemberDtoResponse
import com.example.demo.member.entity.Member
import com.example.demo.member.entity.MemberRole
import com.example.demo.member.entity.RefreshToken
import com.example.demo.member.repository.MemberRepository
import com.example.demo.member.repository.MemberRoleRepository
import com.example.demo.member.repository.RefreshTokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {

    // 회원가입
    @Transactional
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 아이디 입니다.")
        }

        member = memberDtoRequest.toEntity(passwordEncoder.encode(memberDtoRequest.password)!!)
        memberRepository.save(member)

        val memberRole = MemberRole(null, Role.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    // 로그인: 인증 후 Access/Refresh Token 발급, Refresh Token DB 저장
    @Transactional
    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        val tokenInfo = jwtTokenProvider.createToken(authentication)

        val memberId = memberRepository.findByLoginId(loginDto.loginId)?.id
            ?: throw InvalidInputException("loginId", "사용자를 찾을 수 없습니다.")

        // memberId PK로 upsert — 회원 1명당 1개 유지
        val tokenHash = jwtTokenProvider.hashToken(tokenInfo.refreshToken)
        val expiresAt = LocalDateTime.now().plusDays(7)

        val refreshToken = refreshTokenRepository.findByIdOrNull(memberId)
            ?.apply {
                this.tokenHash = tokenHash
                this.expiresAt = expiresAt
            }
            ?: RefreshToken(memberId, tokenHash, expiresAt)
        refreshTokenRepository.save(refreshToken)

        return tokenInfo
    }

    // Refresh Token 검증 후 Access/Refresh Token 재발급 (token rotation)
    @Transactional
    fun reissue(refreshTokenValue: String): TokenInfo {
        // 서명/형식 검증
        val validationResult = jwtTokenProvider.validateToken(refreshTokenValue)
        if (validationResult == TokenValidationResult.INVALID) {
            throw InvalidInputException("refreshToken", "유효하지 않은 Refresh Token입니다.")
        }
        if (validationResult == TokenValidationResult.EXPIRED) {
            throw InvalidInputException("refreshToken", "만료된 Refresh Token입니다. 다시 로그인해주세요.")
        }

        // DB 해시 비교 및 만료 시각 검증
        val memberId = jwtTokenProvider.getUserIdFromToken(refreshTokenValue)
        val stored = refreshTokenRepository.findByIdOrNull(memberId)
            ?: throw InvalidInputException("refreshToken", "Refresh Token이 존재하지 않습니다. 다시 로그인해주세요.")

        if (stored.tokenHash != jwtTokenProvider.hashToken(refreshTokenValue)) {
            throw InvalidInputException("refreshToken", "Refresh Token이 일치하지 않습니다.")
        }
        if (stored.expiresAt.isBefore(LocalDateTime.now())) {
            throw InvalidInputException("refreshToken", "만료된 Refresh Token입니다. 다시 로그인해주세요.")
        }

        // 비밀번호 재검증 없이 회원 정보로 Authentication 구성
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw InvalidInputException("id", "존재하지 않는 사용자입니다.")
        val memberRoles = member.memberRole
            ?: throw InvalidInputException("id", "권한 정보가 없는 사용자입니다.")
        val authorities = memberRoles.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        val principal = CustomUser(member.id!!, member.loginId, "", authorities)
        val authentication = UsernamePasswordAuthenticationToken(principal, "", authorities)
        val newTokenInfo = jwtTokenProvider.createToken(authentication)

        // Refresh Token 교체 저장
        stored.tokenHash = jwtTokenProvider.hashToken(newTokenInfo.refreshToken)
        stored.expiresAt = LocalDateTime.now().plusDays(7)
        refreshTokenRepository.save(stored)

        return newTokenInfo
    }

    // 내 정보 조회
    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member: Member =
            memberRepository.findByIdOrNull(id)
                ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 사용자입니다.")
        return member.toDto()
    }

    // 내 정보 수정
    @Transactional
    fun saveMyInfo(memberDtoRequest: MemberDtoRequest): String {
        val id = memberDtoRequest.id
            ?: throw InvalidInputException("id", "회원 ID가 필요합니다.")
        memberRepository.findByIdOrNull(id)
            ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 사용자입니다.")
        val encodedPassword = passwordEncoder.encode(memberDtoRequest.password)!!
        val member: Member = memberDtoRequest.toEntity(encodedPassword)
        memberRepository.save(member)
        return "수정 완료되었습니다."
    }
}
