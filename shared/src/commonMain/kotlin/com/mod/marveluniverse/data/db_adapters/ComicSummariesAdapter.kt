package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ComicSummaryDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ComicSummariesAdapter: ColumnAdapter<List<ComicSummaryDto>, String> {
    override fun decode(databaseValue: String): List<ComicSummaryDto> {
        if (databaseValue.isEmpty()) return listOf()

        return databaseValue.split(",,,").map { json ->
            Json.decodeFromString(json)
        }
    }

    override fun encode(value: List<ComicSummaryDto>): String {
        return value.joinToString(separator = ",,,") {
            Json.encodeToString(it)
        }
    }
}