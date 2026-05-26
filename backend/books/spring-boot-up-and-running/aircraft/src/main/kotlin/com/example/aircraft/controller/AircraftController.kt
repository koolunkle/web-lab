package com.example.aircraft.controller

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.service.AircraftQueryer
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class AircraftController(
    private val aircraftQueryer: AircraftQueryer
) {

    @GetMapping("/aircraft")
    @ResponseBody
    fun getAircraft(model: Model): List<Aircraft> {
        model.addAttribute("currentAircraft", aircraftQueryer.getAllAircrafts())

        // return "aircraft"
        return aircraftQueryer.getAllAircrafts()
    }
}
