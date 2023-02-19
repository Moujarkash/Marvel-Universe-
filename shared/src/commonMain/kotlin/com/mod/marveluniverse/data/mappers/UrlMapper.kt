package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.UrlDto
import com.mod.marveluniverse.domain.entites.Url

fun UrlDto.toDomainEntity() = Url(
    type = type,
    url = url
)

fun Url.toDataDto() = UrlDto(
    type = type,
    url = url
)