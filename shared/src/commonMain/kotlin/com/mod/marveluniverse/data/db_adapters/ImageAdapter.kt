package com.mod.marveluniverse.data.db_adapters

import app.cash.sqldelight.ColumnAdapter
import com.mod.marveluniverse.data.dtos.ImageDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ImageAdapter: ColumnAdapter<ImageDto, String> {
    override fun decode(databaseValue: String): ImageDto {
        return Json.decodeFromString(databaseValue)
    }

    override fun encode(value: ImageDto): String {
        return Json.encodeToString(value)
    }
}