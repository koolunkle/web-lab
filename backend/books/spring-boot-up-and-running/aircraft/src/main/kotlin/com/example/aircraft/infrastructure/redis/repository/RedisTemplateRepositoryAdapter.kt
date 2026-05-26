package com.example.aircraft.infrastructure.redis.repository

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.domain.repository.AircraftRepository
import com.example.aircraft.infrastructure.redis.entity.AircraftCache
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration

/**
 * RedisTemplate 방식
 * - 장점: Hash 구조 대신 단순 String(JSON) 값을 사용하여 매우 빠르고 가볍습니다.
 * - 단점: findAll, deleteAll 등 목록 기반 작업 시 keys() 검색이 필요해 성능 이슈가 있을 수 있습니다.
 */
// @Repository // 사용 시 주석 해제 (현재는 Cacheable 방식을 실습하기 위해 비활성화)
class RedisTemplateRepositoryAdapter(
    private val redisTemplate: RedisTemplate<String, AircraftCache>
) : AircraftRepository {

    override fun findAll(): List<Aircraft> {
        val keys = redisTemplate.keys("aircraft:*") ?: return emptyList()
        val values = redisTemplate.opsForValue().multiGet(keys) ?: return emptyList()

        return values.filterNotNull().map { it.toDomain() }
    }

    override fun saveAll(aircrafts: List<Aircraft>) {
        sync(aircrafts)
    }

    override fun deleteAll() {
        val keys = redisTemplate.keys("aircraft:*")
        if (!keys.isNullOrEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    override fun sync(aircrafts: List<Aircraft>) {
        redisTemplate.executePipelined { _ ->
            val ops = redisTemplate.opsForValue()

            aircrafts.forEach { plane ->
                val redisEntity = AircraftCache.fromDomain(plane)
                ops.set(
                    "aircraft:${plane.reg}",
                    redisEntity,
                    Duration.ofSeconds(5)
                )
            }
            null
        }
    }
}
