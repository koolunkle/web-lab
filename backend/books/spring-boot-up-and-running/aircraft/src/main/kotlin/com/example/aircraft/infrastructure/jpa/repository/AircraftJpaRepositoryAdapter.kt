package com.example.aircraft.infrastructure.jpa.repository

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.domain.repository.AircraftRepository
import com.example.aircraft.infrastructure.jpa.entity.AircraftEntity
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Primary
@Repository
class AircraftJpaRepositoryAdapter(
    private val jpaRepository: AircraftJpaRepository
) : AircraftRepository {

    override fun findAll(): List<Aircraft> {
        return jpaRepository.findAll().map { it.toDomain() }
    }

    @Transactional
    override fun saveAll(aircrafts: List<Aircraft>) {
        val entities = aircrafts.map { AircraftEntity.fromDomain(it) }
        jpaRepository.saveAll(entities)
    }

    @Transactional
    override fun deleteAll() {
        jpaRepository.deleteAll()
    }

    @Transactional
    override fun sync(aircrafts: List<Aircraft>) {
        val activeRegs = aircrafts.map { it.reg }.toSet()

        jpaRepository.deleteByRegNotIn(activeRegs)

        val existingPlanes = jpaRepository.findByRegIn(activeRegs).associateBy { it.reg }
        val planesToSave = aircrafts.map { plane ->
            val entity = AircraftEntity.fromDomain(plane)
            entity.apply { id = existingPlanes[plane.reg]?.id }
        }

        jpaRepository.saveAll(planesToSave)
    }
}
