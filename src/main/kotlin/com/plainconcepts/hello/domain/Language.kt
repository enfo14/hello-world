package com.plainconcepts.hello.domain

class Language(
    val code: String,
    private val hello: String,
){
    companion object {
        private val English = Language("en", "Hello, world!")
        private val Spanish = Language("es", "¡Hola, mundo!")
        private val Catalan = Language("ca", "Hola, món!")
        private val Basque = Language("eu", "Kaixo Mundua!")
        private val Japanese = Language("jp", "こんにちは、世界！")

        private val all
            get(): List<Language> = listOf(English, Spanish, Catalan, Basque, Japanese)

        fun random(): Language {
            return all.random()
        }

        fun find(code: String): Language {
            return all.first { it.code == code }
        }
    }

    fun greet() = hello
}

