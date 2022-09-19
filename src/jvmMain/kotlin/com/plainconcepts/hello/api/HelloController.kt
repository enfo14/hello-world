package com.plainconcepts.hello.api

import com.plainconcepts.hello.domain.Language
import com.plainconcepts.hello.common.Message
import com.plainconcepts.hello.common.LanguageRequest
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.util.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("hello")
class HelloController(val lang: String? = null)

fun Route.helloController() {
    get<HelloController> { request ->
        val language = try {
            request.lang?.let { Language.find(it) } ?: Language.random()
        } catch (e: NoSuchElementException) {
            return@get call.respond(HttpStatusCode.NotFound, Message("I'm sorry, I don't speak '${request.lang}'"))
        }

        call.respond(Message(language.hello))
    }

    put<HelloController> {
        val language = try {
            val payload = call.receive<LanguageRequest>()
            Language.upsert(payload)
        } catch (e: BadRequestException) {
            return@put call.respond(HttpStatusCode.BadRequest, Message(e.message!!))
        } catch (e: Exception) {
            return@put call.respond(HttpStatusCode.InternalServerError, Message(e.message!!))
        }

        call.response.headers.append(HttpHeaders.Location, call.url { parameters.append("lang", language.code) })
        call.respond(HttpStatusCode.Created)
    }
}