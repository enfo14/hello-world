package com.plainconcepts.hello.common

import kotlinx.serialization.Serializable

@Serializable
data class LanguageDTO(
    val code: String,
    val name: String,
)

@Serializable
data class LanguageRequest(
    val code: String,
    val name: String,
    val hello: String,
) {
    init {
        if (code.isBlank()) throw IllegalArgumentException("Code cannot be blank")
        if (name.isBlank()) throw IllegalArgumentException("Name cannot be blank")
        if (hello.isBlank()) throw IllegalArgumentException("Greetings message cannot be blank")
    }
}