package com.example.demo.member.controller

import com.example.demo.common.authority.TokenInfo
import com.example.demo.common.dto.BaseResponse
import com.example.demo.common.dto.CustomUser
import com.example.demo.member.dto.LoginDto
import com.example.demo.member.dto.MemberDtoRequest
import com.example.demo.member.dto.MemberDtoResponse
import com.example.demo.member.dto.MemberUpdateRequest
import com.example.demo.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/member")
@RestController
class MemberController(private val memberService: MemberService) {

    // 회원가입
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    // 로그인
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data = tokenInfo)
    }

    // Refresh Token으로 새 토큰 재발급
    @PostMapping("/refresh")
    fun refresh(@RequestHeader("Refresh-Token") refreshToken: String): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.reissue(refreshToken)
        return BaseResponse(data = tokenInfo)
    }

    // 로그아웃
    @PostMapping("/logout")
    fun logout(): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication?.principal as CustomUser).userId
        memberService.logout(userId)
        return BaseResponse(message = "로그아웃 되었습니다.")
    }

    // 내 정보 조회
    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<MemberDtoResponse> {
        val userId = (SecurityContextHolder.getContext().authentication?.principal as CustomUser).userId
        val response = memberService.searchMyInfo(userId)
        return BaseResponse(data = response)
    }

    // 내 정보 수정
    @PutMapping("/info")
    fun saveMyInfo(@RequestBody @Valid request: MemberUpdateRequest): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication?.principal as CustomUser).userId
        val resultMsg = memberService.saveMyInfo(userId, request)
        return BaseResponse(message = resultMsg)
    }

    // 회원 탈퇴
    @DeleteMapping("/withdraw")
    fun withdraw(): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication?.principal as CustomUser).userId
        memberService.withdraw(userId)
        return BaseResponse(message = "회원 탈퇴가 완료되었습니다.")
    }
}