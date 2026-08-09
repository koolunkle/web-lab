package com.example.demo.controller

import com.example.demo.dto.SampleDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SampleController {

    @GetMapping("/sample")
    fun sample(model: Model): String {
        val users = listOf(
            SampleDto(1L, "김철수", "ADMIN", true),
            SampleDto(2L, "이영희", "USER", false),
            SampleDto(3L, "박민수", "USER", true)
        )

        model.addAttribute("title", "Thymeleaf 가이드")
        model.addAttribute("users", users)
        model.addAttribute("loginUser", users[0])
        model.addAttribute("nickname", "홍길동")
        model.addAttribute("emptyList", emptyList<SampleDto>())

        return "sample"
    }
}