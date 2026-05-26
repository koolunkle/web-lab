package com.example.aircraft.infrastructure.jpa.entity

import com.example.aircraft.domain.Aircraft
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "aircraft")
class AircraftEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
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
    var vertRate: Int = 0,
    var selectedAltitude: Int = 0,
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var barometer: Double = 0.0,
    var polarDistance: Double = 0.0,
    var polarBearing: Double = 0.0,
    @Column(name = "is_adsb") var isADSB: Boolean = false,
    @Column(name = "is_on_ground") var isOnGround: Boolean = false,
    var lastSeenTime: Instant? = null,
    var posUpdateTime: Instant? = null,
    @Column(name = "bds40_seen_time") var bds40SeenTime: Instant? = null,
) {
    fun toDomain(): Aircraft = Aircraft(
        id, callsign, squawk, reg, flightno, route, type, category,
        altitude, heading, speed, vertRate, selectedAltitude, lat, lon, barometer,
        polarDistance, polarBearing, isADSB, isOnGround, lastSeenTime, posUpdateTime, bds40SeenTime
    )

    companion object {
        fun fromDomain(aircraft: Aircraft): AircraftEntity = AircraftEntity(
            aircraft.id, aircraft.callsign, aircraft.squawk, aircraft.reg, aircraft.flightno, aircraft.route,
            aircraft.type, aircraft.category, aircraft.altitude, aircraft.heading, aircraft.speed,
            aircraft.vertRate, aircraft.selectedAltitude, aircraft.lat, aircraft.lon, aircraft.barometer,
            aircraft.polarDistance, aircraft.polarBearing, aircraft.isADSB, aircraft.isOnGround,
            aircraft.lastSeenTime, aircraft.posUpdateTime, aircraft.bds40SeenTime
        )
    }

    override fun toString(): String = "AircraftEntity(id=$id, reg='$reg', type='$type')"
}
