package com.plainconcepts.hello.common

import kotlinx.serialization.Serializable

@Serializable
data class LanguageDTO(
    val code: String,
    val hello: String,
)