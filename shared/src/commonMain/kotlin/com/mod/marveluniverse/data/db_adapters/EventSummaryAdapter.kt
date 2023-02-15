package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.EventSummaryDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EventSummaryAdapter: ColumnAdapter<EventSummaryDto, String> {
    override fun decode(databaseValue: String): EventSummaryDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: EventSummaryDto): String {
        return Json.encodeToString(value)
    }
}