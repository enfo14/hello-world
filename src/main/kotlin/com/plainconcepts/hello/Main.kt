package com.plainconcepts.hello

import com.plainconcepts.hello.plugins.configureDatabase
import com.plainconcepts.hello.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8000, host = "0.0.0.0") {
        configureDatabase()
        configureRouting()
    }.start(wait = true)
}