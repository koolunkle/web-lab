package com.example.aircraft.service

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.time.Instant
import java.util.concurrent.CopyOnWriteArrayList

@Component
class WebSocketHandler : TextWebSocketHandler() {

    private val sessionList = CopyOnWriteArrayList<WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessionList.add(session)
        println("Connection established from $session @ ${Instant.now()}")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        println("Message received: ${message.payload}, from $session")

        sessionList.forEach { s ->
            try {
                s.sendMessage(message)
                println("--> Sending message to $s")
            } catch (e: Exception) {
                println("Exception sending message to $s: ${e.localizedMessage}")
            }
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList.remove(session)
        println("Connection closed by $session @ ${Instant.now()}")
    }

    fun getSessions(): List<WebSocketSession> = sessionList
}