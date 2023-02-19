package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.TextObjectDto
import com.mod.marveluniverse.domain.entites.TextObject

fun TextObjectDto.toDomainEntity() = TextObject(
    type = type,
    language = language,
    text = text
)

fun TextObject.toDataDto() = TextObjectDto(
    type = type,
    language = language,
    text = text
)