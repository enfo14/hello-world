package com.plainconcepts.hello

fun main(args: Array<String>) {
    val lang = args.firstOrNull()
    println(hello(lang))
}