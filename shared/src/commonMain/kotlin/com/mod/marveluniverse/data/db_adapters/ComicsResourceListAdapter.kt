package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ComicSummaryDto
import com.mod.marveluniverse.data.dtos.ResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ComicsResourceListAdapter: ColumnAdapter<ResourceListDto<ComicSummaryDto>, String> {
    override fun decode(databaseValue: String): ResourceListDto<ComicSummaryDto> {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ResourceListDto<ComicSummaryDto>): String {
        return Json.encodeToString(value)
    }
}