package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.SeriesSummaryDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SeriesSummaryAdapter: ColumnAdapter<SeriesSummaryDto, String> {
    override fun decode(databaseValue: String): SeriesSummaryDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: SeriesSummaryDto): String {
        return Json.encodeToString(value)
    }
}