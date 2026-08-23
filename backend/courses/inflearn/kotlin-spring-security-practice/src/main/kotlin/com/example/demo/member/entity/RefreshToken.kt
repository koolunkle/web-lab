package com.example.demo.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class RefreshToken(
    // PK = memberId: 회원당 1개
    @Id
    val memberId: Long,

    // SHA-256 해시 저장
    @Column(nullable = false, length = 64)
    var tokenHash: String,

    @Column(nullable = false)
    var expiresAt: LocalDateTime,
)
