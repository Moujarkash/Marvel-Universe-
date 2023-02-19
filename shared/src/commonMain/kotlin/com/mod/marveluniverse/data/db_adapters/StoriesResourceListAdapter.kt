package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.StoriesResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StoriesResourceListAdapter: ColumnAdapter<StoriesResourceListDto, String> {
    override fun decode(databaseValue: String): StoriesResourceListDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: StoriesResourceListDto): String {
        return Json.encodeToString(value)
    }
}