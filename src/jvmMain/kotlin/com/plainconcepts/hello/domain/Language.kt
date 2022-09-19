package com.plainconcepts.hello.domain

import com.plainconcepts.hello.common.LanguageRequest
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Random
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Languages : IntIdTable("language") {
    val code = char("code", 2).index()
    val name = varchar("name", 50)
    val hello = text("hello")
}

class Language(id: EntityID<Int>) : IntEntity(id) {

    var code by Languages.code
    var name by Languages.name
    var hello by Languages.hello

    companion object : IntEntityClass<Language>(Languages) {
        fun random(): Language =
            transaction {
                val query = Languages.selectAll().orderBy(Random()).first()
                Language.wrapRow(query)
            }

        fun find(lang: String): Language =
            transaction {
                find { Languages.code.lowerCase() eq lang.lowercase() }.first()
            }

        fun upsert(lang: LanguageRequest): Language =
            transaction {
                val language = find { Languages.code.lowerCase() eq lang.code.lowercase() }.firstOrNull()
                    ?: Language.new {
                        code = lang.code
                        name = lang.name
                    }

                language.hello = lang.hello
                language.flush()
                return@transaction language
            }
    }
}