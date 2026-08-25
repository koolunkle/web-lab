package com.example.aircraft.service

import com.example.aircraft.domain.Aircraft
import com.example.aircraft.domain.repository.AircraftRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.readValue

@Service
class AircraftLoader(
    private val repositories: List<AircraftRepository>,
    private val objectMapper: ObjectMapper
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(AircraftLoader::class.java)

    override fun run(args: ApplicationArguments) {
        val resourcePath = "/aircraft.json"
        val inputStream = javaClass.getResourceAsStream(resourcePath) ?: run {
            log.warn("Data file not found: {}", resourcePath)
            return
        }

        val aircraftList: List<Aircraft> = inputStream.use { stream ->
            objectMapper.readValue(stream)
        }

        if (aircraftList.isEmpty()) {
            log.info("No data found to load")
            return
        }

        repositories.forEach { repo ->
            repo.sync(aircraftList)
        }

        log.info("Loaded {} initial aircraft records", aircraftList.size)
    }
}
