package com.example.aircraft.controller

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.service.AircraftQueryer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest
import org.springframework.cache.CacheManager
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import java.time.Instant
import kotlin.test.assertEquals

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureWebTestClient
@WebFluxTest(AircraftController::class)
class AircraftControllerTest {

    @MockitoBean
    private lateinit var aircraftQueryer: AircraftQueryer

    @MockitoBean
    private lateinit var cacheManager: CacheManager

    private lateinit var ac1: Aircraft
    private lateinit var ac2: Aircraft

    @BeforeEach
    fun setUp() {
        ac1 = Aircraft(
            1L, "SAL001", "sqwk", "N12345", "SAL001",
            "STL-SFO", "LJ", "ct",
            30000, 280, 440, 0, 0,
            39.2979849, -94.71921, 0.0, 0.0, 0.0,
            isADSB = true, isOnGround = false,
            lastSeenTime = Instant.now(), posUpdateTime = Instant.now(), bds40SeenTime = Instant.now()
        )

        ac2 = Aircraft(
            2L, "SAL002", "sqwk", "N54321", "SAL002",
            "SFO-STL", "LJ", "ct",
            40000, 65, 440, 0, 0,
            39.8560963, -104.6759263, 0.0, 0.0, 0.0,
            isADSB = true, isOnGround = false,
            lastSeenTime = Instant.now(), posUpdateTime = Instant.now(), bds40SeenTime = Instant.now()
        )

        given(aircraftQueryer.getAllAircrafts())
            .willReturn(listOf(ac1, ac2))
    }

    @Test
    fun getAircraft(@Autowired client: WebTestClient) {
        val aircrafts = client.get()
            .uri("/aircraft")
            .exchange()
            .expectStatus().isOk
            .expectBodyList<Aircraft>()
            .returnResult().responseBody

        // assertThat(aircrafts).isNotEmpty
        assertEquals(listOf(ac1, ac2), aircrafts)
    }

    @AfterEach
    fun tearDown() {
    }
}