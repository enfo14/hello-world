package com.plainconcepts.hello.api

import com.plainconcepts.hello.common.LanguageDTO
import com.plainconcepts.hello.domain.Language
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
@Resource("languages")
class LanguageController


fun Route.languageController() {
    get<LanguageController> {
        val languages = transaction { Language.all().map { LanguageDTO(it.code, it.name) } }
        call.respond(languages)
    }
}