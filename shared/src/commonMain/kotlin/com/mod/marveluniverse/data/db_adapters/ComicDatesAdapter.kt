package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ComicDateDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ComicDatesAdapter: ColumnAdapter<List<ComicDateDto>, String> {
    override fun decode(databaseValue: String): List<ComicDateDto> {
        if (databaseValue.isEmpty()) return listOf()

        return databaseValue.split(",").map { json ->
            Json.decodeFromString(json)
        }
    }

    override fun encode(value: List<ComicDateDto>): String {
        return value.joinToString(separator = ",") {
            Json.encodeToString(it)
        }
    }
}