package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.CharacterSummaryDto
import com.mod.marveluniverse.data.dtos.ResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CharactersResourceListAdapter: ColumnAdapter<ResourceListDto<CharacterSummaryDto>, String> {
    override fun decode(databaseValue: String): ResourceListDto<CharacterSummaryDto> {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ResourceListDto<CharacterSummaryDto>): String {
        return Json.encodeToString(value)
    }
}