package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ComicSummaryDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ComicSummaryAdapter: ColumnAdapter<ComicSummaryDto, String> {
    override fun decode(databaseValue: String): ComicSummaryDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ComicSummaryDto): String {
        return Json.encodeToString(value)
    }
}