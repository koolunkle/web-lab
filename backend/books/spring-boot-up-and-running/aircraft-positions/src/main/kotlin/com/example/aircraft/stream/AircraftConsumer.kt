package com.example.aircraft.stream

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.service.AircraftSyncer
import com.example.aircraft.service.WebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.TextMessage
import tools.jackson.databind.ObjectMapper

@Configuration
class AircraftConsumer(
    private val aircraftSyncer: AircraftSyncer,
    private val webSocketHandler: WebSocketHandler,
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun retrieveAircraftPositions(): (List<Aircraft>) -> Unit = { aircrafts ->
        val updatedAircrafts = aircraftSyncer.syncPlanes(aircrafts)
        if (updatedAircrafts.isNotEmpty()) {
            val json = objectMapper.writeValueAsString(updatedAircrafts)
            webSocketHandler.getSessions().forEach { session ->
                try {
                    session.sendMessage(TextMessage(json))
                } catch (e: Exception) {
                    println("--- Error sending WebSocket message: ${e.message}")
                }
            }
        }
    }
}
