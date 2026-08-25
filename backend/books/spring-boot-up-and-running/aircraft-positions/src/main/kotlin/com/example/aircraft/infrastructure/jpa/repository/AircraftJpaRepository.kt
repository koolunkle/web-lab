package com.example.aircraft.infrastructure.jpa.repository

import com.example.aircraft.infrastructure.jpa.entity.AircraftEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface AircraftJpaRepository : JpaRepository<AircraftEntity, Long> {

    fun findByRegIn(regs: Collection<String>): List<AircraftEntity>

    @Modifying
    @Query("DELETE FROM AircraftEntity a WHERE a.reg NOT IN :activeRegs")
    fun deleteByRegNotIn(activeRegs: Collection<String>)
}
