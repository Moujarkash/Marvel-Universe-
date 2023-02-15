package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.TextObjectDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TextObjectsAdapter : ColumnAdapter<List<TextObjectDto>, String> {
    override fun decode(databaseValue: String): List<TextObjectDto> {
        if (databaseValue.isEmpty())
            return listOf()

        return databaseValue.split(",").map { json ->
            Json.decodeFromString(json)
        }
    }

    override fun encode(value: List<TextObjectDto>): String {
        return value.joinToString(separator = ",") {
            Json.encodeToString(it)
        }
    }
}