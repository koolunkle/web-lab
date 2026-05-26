package com.example.aircraft.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class Aircraft(
    var id: Long? = null,
    var callsign: String? = "",
    var squawk: String? = "",
    var reg: String = "",
    var flightno: String? = "",
    var route: String? = "",
    var type: String? = "",
    var category: String? = "",
    var altitude: Int = 0,
    var heading: Int = 0,
    var speed: Int = 0,
    @JsonProperty("vert_rate") var vertRate: Int = 0,
    @JsonProperty("selected_altitude") var selectedAltitude: Int = 0,
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var barometer: Double = 0.0,
    @JsonProperty("polar_distance") var polarDistance: Double = 0.0,
    @JsonProperty("polar_bearing") var polarBearing: Double = 0.0,
    @JsonProperty("is_adsb") var isADSB: Boolean = false,
    @JsonProperty("is_on_ground") var isOnGround: Boolean = false,
    @JsonProperty("last_seen_time") var lastSeenTime: Instant? = null,
    @JsonProperty("pos_update_time") var posUpdateTime: Instant? = null,
    @JsonProperty("bds40_seen_time") var bds40SeenTime: Instant? = null,
)
