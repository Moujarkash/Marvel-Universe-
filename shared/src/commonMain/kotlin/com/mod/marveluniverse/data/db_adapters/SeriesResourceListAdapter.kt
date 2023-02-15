package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ResourceListDto
import com.mod.marveluniverse.data.dtos.SeriesSummaryDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SeriesResourceListAdapter: ColumnAdapter<ResourceListDto<SeriesSummaryDto>, String> {
    override fun decode(databaseValue: String): ResourceListDto<SeriesSummaryDto> {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ResourceListDto<SeriesSummaryDto>): String {
        return Json.encodeToString(value)
    }
}