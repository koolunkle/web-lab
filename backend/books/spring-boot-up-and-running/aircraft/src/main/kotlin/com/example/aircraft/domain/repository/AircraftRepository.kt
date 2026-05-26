package com.example.aircraft.domain.repository

import com.example.aircraft.domain.Aircraft

/**
 * 저장소 기술(JPA, Redis 등)에 의존하지 않는 순수 도메인 리포지토리 인터페이스
 */
interface AircraftRepository {
    fun findAll(): List<Aircraft>
    fun saveAll(aircrafts: List<Aircraft>)
    fun deleteAll()

    /**
     * 현재 활성화된 항공기 목록으로 저장소를 동기화
     * (기존 데이터 중 목록에 없는 것은 삭제하고, 있는 것은 업데이트/추가)
     */
    fun sync(aircrafts: List<Aircraft>)
}
