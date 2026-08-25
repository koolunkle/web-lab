package com.example.aircraft.infrastructure.redis.entity

import com.example.aircraft.domain.Aircraft
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.time.Instant

@RedisHash("aircraft")
@JsonIgnoreProperties(ignoreUnknown = true)
data class AircraftCache(
    @Id var id: String? = null,
    val callsign: String? = "",
    val squawk: String? = "",
    val reg: String = "",
    val flightno: String? = "",
    val route: String? = "",
    val type: String? = "",
    val category: String? = "",
    val altitude: Int = 0,
    val heading: Int = 0,
    val speed: Int = 0,
    @JsonProperty("vert_rate") val vertRate: Int = 0,
    @JsonProperty("selected_altitude") val selectedAltitude: Int = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val barometer: Double = 0.0,
    @JsonProperty("polar_distance") val polarDistance: Double = 0.0,
    @JsonProperty("polar_bearing") val polarBearing: Double = 0.0,
    @JsonProperty("is_adsb") val isADSB: Boolean = false,
    @JsonProperty("is_on_ground") val isOnGround: Boolean = false,
    var lastSeenTime: Instant? = null,
    var posUpdateTime: Instant? = null,
    var bds40SeenTime: Instant? = null,
    @TimeToLive val ttl: Long = 5L
) {
    fun toDomain(): Aircraft = Aircraft(
        callsign = callsign, squawk = squawk, reg = reg, flightno = flightno, route = route,
        type = type, category = category, altitude = altitude, heading = heading, speed = speed,
        vertRate = vertRate, selectedAltitude = selectedAltitude, lat = lat, lon = lon,
        barometer = barometer, polarDistance = polarDistance, polarBearing = polarBearing,
        isADSB = isADSB, isOnGround = isOnGround, lastSeenTime = lastSeenTime,
        posUpdateTime = posUpdateTime, bds40SeenTime = bds40SeenTime
    )

    companion object {
        fun fromDomain(aircraft: Aircraft): AircraftCache = AircraftCache(
            id = aircraft.reg,
            callsign = aircraft.callsign, squawk = aircraft.squawk, reg = aircraft.reg,
            flightno = aircraft.flightno, route = aircraft.route, type = aircraft.type,
            category = aircraft.category, altitude = aircraft.altitude, heading = aircraft.heading,
            speed = aircraft.speed, vertRate = aircraft.vertRate, selectedAltitude = aircraft.selectedAltitude,
            lat = aircraft.lat, lon = aircraft.lon, barometer = aircraft.barometer,
            polarDistance = aircraft.polarDistance, polarBearing = aircraft.polarBearing,
            isADSB = aircraft.isADSB, isOnGround = aircraft.isOnGround,
            lastSeenTime = aircraft.lastSeenTime, posUpdateTime = aircraft.posUpdateTime,
            bds40SeenTime = aircraft.bds40SeenTime
        )
    }
}
