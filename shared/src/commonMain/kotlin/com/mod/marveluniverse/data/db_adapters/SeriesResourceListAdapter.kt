package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.SeriesResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SeriesResourceListAdapter: ColumnAdapter<SeriesResourceListDto, String> {
    override fun decode(databaseValue: String): SeriesResourceListDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: SeriesResourceListDto): String {
        return Json.encodeToString(value)
    }
}