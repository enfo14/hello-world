package com.plainconcepts.hello.api

import com.plainconcepts.hello.plugins.configureMockDatabase
import com.plainconcepts.hello.plugins.configureRouting
import com.plainconcepts.hello.plugins.testClient
import com.plainconcepts.hello.viewmodels.Message
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetHelloTest {

    val values = listOf(
        "Hello, world!",
        "¡Hola, mundo!",
        "Hola, món!",
        "Kaixo Mundua!",
        "こんにちは、世界！"
    )

    @Test
    fun `should return hello world`() = testApplication {
        application {
            configureMockDatabase()
            configureRouting()
        }
        val resp = testClient.get("/")
        Assertions.assertTrue(values.contains(resp.body<Message>().message))
    }

    private fun getData(): List<Arguments> {
        return listOf(
            Arguments.of("en", "Hello, world!"),
            Arguments.of("es", "¡Hola, mundo!"),
            Arguments.of("ca", "Hola, món!"),
            Arguments.of("eu", "Kaixo Mundua!"),
            Arguments.of("jp", "こんにちは、世界！"),
        )
    }

    @ParameterizedTest(name = "should return hello world in {0}")
    @MethodSource("getData")
    fun `should return hello world in the selected language`(lang: String, result: String) = testApplication {
        application {
            configureMockDatabase()
            configureRouting()
        }
        val resp = testClient.get("/") { url {parameters.append("lang", lang) } }
        Assertions.assertEquals(result, resp.body<Message>().message)
    }

    @Test
    fun `should return a 404 Not Found if the requested language is not found`() = testApplication {
        application {
            configureMockDatabase()
            configureRouting()
        }
        val resp = testClient.get("/") { url { parameters.append("lang", "ru") } }
        Assertions.assertEquals(HttpStatusCode.NotFound, resp.status)
        Assertions.assertEquals("I'm sorry, I don't speak 'ru'", resp.body<Message>().message)
    }
}
