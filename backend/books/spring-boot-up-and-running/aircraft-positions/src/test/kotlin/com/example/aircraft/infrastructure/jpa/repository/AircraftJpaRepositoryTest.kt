package com.example.aircraft.infrastructure.jpa.repository

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.infrastructure.jpa.entity.AircraftEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.cache.CacheManager
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.time.Duration
import java.time.Instant
import kotlin.test.Test

@DataJpaTest
class AircraftJpaRepositoryTest @Autowired constructor(
    private val repository: AircraftJpaRepository
) {

    @MockitoBean
    private lateinit var cacheManager: CacheManager

    private lateinit var savedAc1: AircraftEntity
    private lateinit var savedAc2: AircraftEntity

    @BeforeEach
    fun setUp() {
        val ac1 = Aircraft(
            null,
            "SAL001", "sqwk", "N12345", "SAL001", "STL-SFO", "LJ", "ct",
            30000, 280, 440, 0, 0,
            39.2979849, -94.71921, 0.0, 0.0, 0.0,
            isADSB = true, isOnGround = false,
            lastSeenTime = Instant.now(), posUpdateTime = Instant.now(), bds40SeenTime = Instant.now()
        )

        val ac2 = Aircraft(
            null,
            "SAL002", "sqwk", "N54321", "SAL002", "SFO-STL", "LJ", "ct",
            40000, 65, 440, 0, 0,
            39.8560963, -104.6759263, 0.0, 0.0, 0.0,
            isADSB = true, isOnGround = false,
            lastSeenTime = Instant.now(), posUpdateTime = Instant.now(), bds40SeenTime = Instant.now()
        )

        val savedEntities = repository.saveAll(
            listOf(
                AircraftEntity.fromDomain(ac1),
                AircraftEntity.fromDomain(ac2)
            )
        )

        savedAc1 = savedEntities[0]
        savedAc2 = savedEntities[1]
    }

    @Test
    fun `should find all aircraft entities`() {
        // when
        val results = repository.findAll()

        // then
        assertThat(results).hasSize(2)
        assertThat(results).containsExactlyInAnyOrder(savedAc1, savedAc2)
    }

    @Test
    fun `should find specific aircraft entity by ID`() {
        // given
        val targetId = savedAc1.id!!

        // when
        val result = repository.findById(targetId)

        // then
        // assertThat(result).isPresent
        // assertThat(result.get().callsign).isEqualTo("SAL001")

        assertThat(result.get())
            .usingRecursiveComparison()
            // .ignoringFields("lastSeenTime", "posUpdateTime", "bds40SeenTime")
            .withEqualsForType({ t1, t2 ->
                Duration.between(t1, t2).abs().seconds <= 1
            }, Instant::class.java)
            .isEqualTo(savedAc1)
    }

    @Test
    fun `should find entities by registration collection`() {
        // given
        val regs = listOf("N12345", "N54321")

        // when
        val results = repository.findByRegIn(regs)

        // then
        assertThat(results).hasSize(2)
        assertThat(results.map { it.reg }).containsExactlyInAnyOrder("N12345", "N54321")
    }

    @Test
    fun `should delete entities not in active registration collection`() {
        // given
        val activeRegs = listOf("N12345")

        // when
        repository.deleteByRegNotIn(activeRegs)
        val remaining = repository.findAll()

        // then
        assertThat(remaining).hasSize(1)
        assertThat(remaining[0].reg).isEqualTo("N12345")
    }

    // @AfterEach
    //fun tearDown() {
    //    repository.deleteAll()
    //}
}