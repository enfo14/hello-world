package com.plainconcepts.hello.plugins

import com.plainconcepts.hello.api.helloController
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(ContentNegotiation) { json() }
    install(IgnoreTrailingSlash)
    install(Resources)

    routing {
        get("/") {
            call.respondText(
                this::class.java.classLoader.getResource("index.html")!!.readText(),
                ContentType.Text.Html
            )
        }

        static("/") {
            resources("")
        }

        route("/api") {
            helloController()
        }
    }
}
