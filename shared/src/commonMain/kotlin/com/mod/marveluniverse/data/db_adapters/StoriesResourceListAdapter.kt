package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ResourceListDto
import com.mod.marveluniverse.data.dtos.StorySummaryDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StoriesResourceListAdapter: ColumnAdapter<ResourceListDto<StorySummaryDto>, String> {
    override fun decode(databaseValue: String): ResourceListDto<StorySummaryDto> {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ResourceListDto<StorySummaryDto>): String {
        return Json.encodeToString(value)
    }
}