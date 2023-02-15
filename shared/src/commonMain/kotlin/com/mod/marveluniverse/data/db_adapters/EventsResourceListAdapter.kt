package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.EventSummaryDto
import com.mod.marveluniverse.data.dtos.ResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EventsResourceListAdapter: ColumnAdapter<ResourceListDto<EventSummaryDto>, String> {
    override fun decode(databaseValue: String): ResourceListDto<EventSummaryDto> {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ResourceListDto<EventSummaryDto>): String {
        return Json.encodeToString(value)
    }
}