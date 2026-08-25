package com.example.aircraft.service

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.domain.repository.AircraftRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class AircraftQueryer(
    private val repository: AircraftRepository
) {

    /**
     * 항공기 목록을 조회하고 결과를 Redis에 캐싱
     * cacheNames: "aircrafts"라는 이름으로 캐시 공간을 사용
     * key: 고정 키값을 사용하여 목록 전체를 하나의 캐시에 저장 (파라미터가 없으므로 생략 시 기본 키 생성기가 작동하지만, 명확하게 지정 가능)
     */
    @Cacheable(cacheNames = ["aircrafts"], key = "'all'")
    fun getAllAircrafts(): List<Aircraft> {
        return repository.findAll()
    }
}
