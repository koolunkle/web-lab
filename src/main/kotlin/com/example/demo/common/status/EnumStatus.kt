package com.example.demo.common.status

enum class Gender(val desc: String) {
    MAN("남"),
    WOMAN("여")
}

enum class ResultCode(val msg: String) {
    SUCCESS("정상 처리 되었습니다."),
    ERROR("에러가 발생했습니다")
}

enum class Role {
    MEMBER
}

enum class TokenValidationResult {
    VALID,    // 정상
    EXPIRED,  // 만료 (refresh 흐름으로 진입)
    INVALID,  // 서명/형식 오류 등 복구 불가
}