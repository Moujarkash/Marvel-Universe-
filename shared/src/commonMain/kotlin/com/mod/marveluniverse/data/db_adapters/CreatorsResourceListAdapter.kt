package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.CreatorsResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CreatorsResourceListAdapter: ColumnAdapter<CreatorsResourceListDto, String> {
    override fun decode(databaseValue: String): CreatorsResourceListDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: CreatorsResourceListDto): String {
        return Json.encodeToString(value)
    }
}