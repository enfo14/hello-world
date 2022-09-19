package com.plainconcepts.hello.api

import com.plainconcepts.hello.plugins.configureMockDatabase
import com.plainconcepts.hello.plugins.configureRouting
import com.plainconcepts.hello.plugins.testClient
import com.plainconcepts.hello.common.Message
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CreateHelloTest {

    @Test
    fun `should return a Bad Request status if a field is missing`() = testApplication {
        application {
            configureMockDatabase()
            configureRouting()
        }
        val resp = testClient.put("/api/hello") { setBody("""{"code": "fr"}""") }
        Assertions.assertEquals(HttpStatusCode.BadRequest, resp.status)
    }

    @Test
    fun `should return a 201 Created with a Location`() = testApplication {
        application {
            configureMockDatabase()
            configureRouting()
        }
        val resp = testClient.put("/api/hello") { setBody("""{"code": "fr", "name": "Français", "hello": "Bonjour, monde!"}""".trimMargin())}
        Assertions.assertEquals(HttpStatusCode.Created, resp.status)
        Assertions.assertEquals("http://localhost/api/hello?lang=fr", resp.headers[HttpHeaders.Location])
    }

    @Test
    fun `should create a persistent record`() = testApplication {
        application {
            configureMockDatabase()
            configureRouting()
        }
        testClient.put("/api/hello") { setBody("""{"code": "fr", "name": "Français", "hello": "Bonjour, monde!"}""")}
        val resp = testClient.get("/api/hello?lang=fr").body<Message>()
        Assertions.assertEquals("Bonjour, monde!", resp.message)
    }

    @Test
    fun `should update an existing record`() = testApplication {
        application {
            configureMockDatabase()
            configureRouting()
        }
        testClient.put("/api/hello") { setBody("""{"code": "fr", "name": "Français", "hello": "Bonjour, monde!"}""")}
        testClient.put("/api/hello") { setBody("""{"code": "fr", "name": "Français", "hello": "Salut, monde!"}""")}
        val resp = testClient.get("/api/hello?lang=fr").body<Message>()
        Assertions.assertEquals("Salut, monde!", resp.message)
    }
}