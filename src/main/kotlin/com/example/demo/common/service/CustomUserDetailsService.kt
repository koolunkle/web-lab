package com.example.demo.common.service

import com.example.demo.common.dto.CustomUser
import com.example.demo.member.entity.Member
import com.example.demo.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        memberRepository.findByLoginId(username)
            ?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다.")

    private fun createUserDetails(member: Member): UserDetails = CustomUser(
        member.id!!,
        member.loginId,
        member.password,   // 저장 시 인코딩된 값 사용
        member.memberRole!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
    )
}