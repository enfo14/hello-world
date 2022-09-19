package com.plainconcepts.hello.api

import com.plainconcepts.hello.common.LanguageDTO
import com.plainconcepts.hello.plugins.configureMockDatabase
import com.plainconcepts.hello.plugins.configureRouting
import com.plainconcepts.hello.plugins.testClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GetLanguagesTest {
    @Test
    fun `should return a list of languages`() = testApplication {
        application {
            configureMockDatabase()
            configureRouting()
        }
        val resp = testClient.get("/api/languages").body<List<LanguageDTO>>()
        Assertions.assertTrue(resp.contains(LanguageDTO("eu", "Euskara")))
    }
}