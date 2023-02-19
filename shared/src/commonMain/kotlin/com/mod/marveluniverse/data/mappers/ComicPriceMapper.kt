package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.ComicPriceDto
import com.mod.marveluniverse.domain.entites.ComicPrice

fun ComicPriceDto.toDomainEntity() = ComicPrice(
    type = type,
    price = price
)

fun ComicPrice.toDataDto() = ComicPriceDto(
    type = type,
    price = price
)