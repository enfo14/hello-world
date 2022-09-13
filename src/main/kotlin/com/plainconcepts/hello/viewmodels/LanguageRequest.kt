package com.plainconcepts.hello.viewmodels

import kotlinx.serialization.Serializable

@Serializable
data class LanguageRequest(
    val code: String,
    val hello: String,
)