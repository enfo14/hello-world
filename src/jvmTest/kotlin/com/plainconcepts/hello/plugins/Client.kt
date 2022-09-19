package com.plainconcepts.hello.plugins

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*

val ApplicationTestBuilder.testClient: HttpClient
    get() {
        return createClient {
            install(ContentNegotiation) { json() }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

