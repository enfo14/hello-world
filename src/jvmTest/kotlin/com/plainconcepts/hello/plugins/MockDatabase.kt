package com.plainconcepts.hello.plugins

import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

fun Application.configureMockDatabase() {
    val source = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;"
    Flyway.configure().dataSource(source, "root", "").load().migrate()
    Database.connect(source, "org.h2.Driver", "root", "")
}
