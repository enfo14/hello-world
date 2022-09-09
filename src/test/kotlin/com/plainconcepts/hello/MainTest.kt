package com.plainconcepts.hello

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HelloTest {
    @Test
    fun `should return hello world`() {
        Assertions.assertEquals("Hello, world!", hello())
    }
}