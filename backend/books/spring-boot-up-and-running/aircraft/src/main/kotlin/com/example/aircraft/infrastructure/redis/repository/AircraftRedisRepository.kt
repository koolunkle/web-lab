package com.example.aircraft.infrastructure.redis.repository

import com.example.aircraft.infrastructure.redis.entity.AircraftCache
import org.springframework.data.repository.CrudRepository

interface AircraftRedisRepository : CrudRepository<AircraftCache, String>
