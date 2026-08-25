//package com.example.aircraft.service
//
//import com.example.aircraft.domain.Aircraft
//import org.springframework.scheduling.annotation.Scheduled
//import org.springframework.stereotype.Service
//import org.springframework.web.reactive.function.client.WebClient
//import org.springframework.web.reactive.function.client.bodyToFlux
//import reactor.core.publisher.Flux
//
//@Service
//class AircraftPoller(
//    private val aircraftSyncer: AircraftSyncer,
//    private val client: WebClient,
//) {
//
//    @Scheduled(fixedDelay = 1000L)
//    fun pollPlanes() {
//        val currentPlanes = getAircraft()
//            .filter { it.reg.isNotBlank() }
//            .doOnError { println("--- Error fetching data: ${it.message}") }
//            .onErrorResume { Flux.empty() }
//            .collectList()
//            .block() ?: emptyList()
//
//        if (currentPlanes.isEmpty()) {
//            return
//        }
//
//        aircraftSyncer.syncPlanes(currentPlanes)
//    }
//
//    private fun getAircraft(): Flux<Aircraft> = client
//        .get()
//        .uri("/aircraft")
//        .retrieve()
//        .bodyToFlux<Aircraft>()
//}
