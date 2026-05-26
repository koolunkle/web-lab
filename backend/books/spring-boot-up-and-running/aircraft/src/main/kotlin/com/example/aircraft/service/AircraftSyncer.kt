package com.example.aircraft.service

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.domain.repository.AircraftRepository
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CachePut
import org.springframework.stereotype.Service

@Service
class AircraftSyncer(
    private val repositories: List<AircraftRepository>
) {

    private val log = LoggerFactory.getLogger(AircraftSyncer::class.java)

    @CachePut(cacheNames = ["aircrafts"], key = "'all'")
    fun syncPlanes(currentPlanes: List<Aircraft>) : List<Aircraft> {
        repositories.forEach { repo ->
            repo.sync(currentPlanes)
        }

        log.info("Synced {} aircraft records across {} data stores (DB/Redis)", currentPlanes.size, repositories.size)

        return currentPlanes
    }
}
