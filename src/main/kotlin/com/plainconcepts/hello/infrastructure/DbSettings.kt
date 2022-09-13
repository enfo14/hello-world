package com.plainconcepts.hello.infrastructure

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

object DbSettings {
    private val HOST = System.getenv("DATABASE_HOST") ?: "localhost"
    private val PORT = System.getenv("DATABASE_PORT") ?: "5432"
    private val NAME = System.getenv("DATABASE_NAME") ?: "hello"
    private val USER = System.getenv("DATABASE_USER") ?: "postgres"
    private val PASS = System.getenv("DATABASE_PASS")

    val source: DataSource by lazy {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://$HOST:$PORT/$NAME"
        config.username = USER
        config.password = PASS
        return@lazy HikariDataSource(config)
    }
}