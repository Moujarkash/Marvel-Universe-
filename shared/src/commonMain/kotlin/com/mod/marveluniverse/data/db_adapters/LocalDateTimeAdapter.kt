package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDateTime

class LocalDateTimeAdapter: ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String): LocalDateTime {
        return LocalDateTime.parse(databaseValue)
    }

    override fun encode(value: LocalDateTime): String {
        return value.toString()
    }
}