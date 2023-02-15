package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ImageDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ImagesAdapter: ColumnAdapter<List<ImageDto>, String> {
    override fun decode(databaseValue: String): List<ImageDto> {
        if (databaseValue.isEmpty()) return listOf()

        return databaseValue.split(",").map { json ->
            Json.decodeFromString(json)
        }
    }

    override fun encode(value: List<ImageDto>): String {
        return value.joinToString(separator = ",") {
            Json.encodeToString(it)
        }
    }
}