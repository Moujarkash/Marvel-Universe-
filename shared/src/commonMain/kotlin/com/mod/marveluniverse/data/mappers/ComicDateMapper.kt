package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.ComicDateDto
import com.mod.marveluniverse.domain.entites.ComicDate

fun ComicDateDto.toDomainEntity() = ComicDate(
    type = type,
    date = date
)

fun ComicDate.toDataDto() = ComicDateDto(
    type = type,
    date = date
)