package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.CreatorSummaryDto
import com.mod.marveluniverse.data.dtos.ResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CreatorsResourceListAdapter: ColumnAdapter<ResourceListDto<CreatorSummaryDto>, String> {
    override fun decode(databaseValue: String): ResourceListDto<CreatorSummaryDto> {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ResourceListDto<CreatorSummaryDto>): String {
        return Json.encodeToString(value)
    }
}