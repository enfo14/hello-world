package com.plainconcepts.hello

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HelloTest {

    val values = listOf(
        "Hello, world!",
        "¡Hola, mundo!",
        "Hola, món!",
        "Kaixo Mundua!",
        "こんにちは、世界！"
    )

    @Test
    fun `should return hello world`() {
        Assertions.assertTrue(values.contains(hello()))
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
    fun `should return hello world in the selected language`(lang: String, result: String) {
        Assertions.assertEquals(result, hello(lang))
    }
}
