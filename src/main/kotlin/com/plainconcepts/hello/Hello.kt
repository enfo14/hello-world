package com.plainconcepts.hello

import com.plainconcepts.hello.domain.Language

fun hello(lang: String? = null): String {
    val language = lang?.let { Language.find(lang) } ?: Language.random()
    return language.greet()
}
