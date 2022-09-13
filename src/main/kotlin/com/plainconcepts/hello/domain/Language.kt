package com.plainconcepts.hello.domain

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Random
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Languages : IntIdTable("hello") {
    val code = char("code", 2).index()
    val hello = text("hello")
}

class Language(id: EntityID<Int>) : IntEntity(id) {

    val code by Languages.code
    private val hello by Languages.hello

    fun greet() = hello

    companion object : IntEntityClass<Language>(Languages) {
        fun random(): Language =
            transaction {
                val query = Languages.selectAll().orderBy(Random()).first()
                Language.wrapRow(query)
            }

        fun find(lang: String): Language =
            transaction {
                Language.find { Languages.code.lowerCase() eq lang.lowercase() }.first()
            }
    }

}