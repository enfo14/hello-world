package com.plainconcepts.hello.plugins

import com.plainconcepts.hello.api.helloController
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(ContentNegotiation) { json() }
    install(IgnoreTrailingSlash)
    install(Resources)

    routing {
        helloController()
    }
}
