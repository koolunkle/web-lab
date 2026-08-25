package com.thehecklers.planefinder;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AircraftPositionsPublisher {
    private final PlaneFinderService pfService;
    private final StreamBridge streamBridge;

    public AircraftPositionsPublisher(PlaneFinderService pfService, StreamBridge streamBridge) {
        this.pfService = pfService;
        this.streamBridge = streamBridge;
    }

    @Scheduled(fixedDelay = 5000L)
    public void publishPositions() {
        List<Aircraft> positions = pfService.getAircraft().collectList().block();

        if (positions != null && !positions.isEmpty()) {
            boolean sent = streamBridge.send("aircraftPositions-out-0", positions);
            System.out.println("--- Published " + positions.size() + " aircraft positions, sent=" + sent);
        }
    }
}
