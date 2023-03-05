package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.UrlDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UrlsAdapter : ColumnAdapter<List<UrlDto>, String> {
    override fun decode(databaseValue: String): List<UrlDto> {
        if (databaseValue.isEmpty())
            return listOf()

        return databaseValue.split(",,,").map { json ->
            Json.decodeFromString(json)
        }
    }

    override fun encode(value: List<UrlDto>): String {
        return value.joinToString(separator = ",,,") {
            Json.encodeToString(it)
        }
    }
}