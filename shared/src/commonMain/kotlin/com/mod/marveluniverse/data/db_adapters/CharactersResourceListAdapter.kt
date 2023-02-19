package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.CharactersResourceListDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CharactersResourceListAdapter : ColumnAdapter<CharactersResourceListDto, String> {
    override fun decode(databaseValue: String): CharactersResourceListDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: CharactersResourceListDto): String {
        return Json.encodeToString(value)
    }
}