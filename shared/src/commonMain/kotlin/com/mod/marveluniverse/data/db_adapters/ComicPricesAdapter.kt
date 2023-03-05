package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ComicPriceDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ComicPricesAdapter: ColumnAdapter<List<ComicPriceDto>, String> {
    override fun decode(databaseValue: String): List<ComicPriceDto> {
        if (databaseValue.isEmpty()) return listOf()

        return databaseValue.split(",,,").map { json ->
            Json.decodeFromString(json)
        }
    }

    override fun encode(value: List<ComicPriceDto>): String {
        return value.joinToString(separator = ",,,") {
            Json.encodeToString(it)
        }
    }
}