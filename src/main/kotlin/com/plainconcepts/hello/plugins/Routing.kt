package com.plainconcepts.hello.plugins

import com.plainconcepts.hello.domain.Language
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(IgnoreTrailingSlash)

    routing {
        get("/") {
            val lang = call.request.queryParameters["lang"]

            val language = try {
                lang?.let { Language.find(lang) } ?: Language.random()
            } catch (e: NoSuchElementException) {
                return@get call.respond(HttpStatusCode.NotFound, "I'm sorry, I don't speak '${lang}'")
            }

            call.respond(language.greet())
        }
    }
}
