package com.plainconcepts.hello.plugins

import com.plainconcepts.hello.infrastructure.DbSettings
import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    Flyway.configure().dataSource(DbSettings.source).load().migrate()
    Database.connect(DbSettings.source)
}