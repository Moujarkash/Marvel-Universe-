package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ComicsResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ComicsResourceListAdapter: ColumnAdapter<ComicsResourceListDto, String> {
    override fun decode(databaseValue: String): ComicsResourceListDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ComicsResourceListDto): String {
        return Json.encodeToString(value)
    }
}