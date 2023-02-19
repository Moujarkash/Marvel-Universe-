package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.EventsResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EventsResourceListAdapter: ColumnAdapter<EventsResourceListDto, String> {
    override fun decode(databaseValue: String): EventsResourceListDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: EventsResourceListDto): String {
        return Json.encodeToString(value)
    }
}