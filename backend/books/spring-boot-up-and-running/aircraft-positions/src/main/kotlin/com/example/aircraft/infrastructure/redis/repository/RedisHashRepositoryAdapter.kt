package com.example.aircraft.infrastructure.redis.repository

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.domain.repository.AircraftRepository
import com.example.aircraft.infrastructure.redis.entity.AircraftCache

/**
 * Spring Data Redis Repository (Hash) 방식
 * - 장점: JPA와 사용법이 동일하여 학습 곡선이 낮습니다.
 * - 단점: 내부적으로 인덱스용 Set 자료구조를 추가로 관리하여 쓰기 오버헤드가 있습니다.
 */
// @Repository // 사용 시 주석 해제 (현재는 Cacheable 방식을 실습하기 위해 비활성화)
class RedisHashRepositoryAdapter(
    private val redisRepository: AircraftRedisRepository
) : AircraftRepository {

    override fun findAll(): List<Aircraft> {
        return redisRepository.findAll().mapNotNull { it.toDomain() }
    }

    override fun saveAll(aircrafts: List<Aircraft>) {
        val caches = aircrafts.map { AircraftCache.fromDomain(it) }
        redisRepository.saveAll(caches)
    }

    override fun deleteAll() {
        redisRepository.deleteAll()
    }

    override fun sync(aircrafts: List<Aircraft>) {
        deleteAll()
        saveAll(aircrafts)
    }
}
