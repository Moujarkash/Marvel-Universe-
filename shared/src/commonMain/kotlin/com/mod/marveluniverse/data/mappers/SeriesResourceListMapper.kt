package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.SeriesResourceListDto
import com.mod.marveluniverse.domain.entites.SeriesResourceList

fun SeriesResourceListDto.toDomainEntity() = SeriesResourceList(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDomainEntity() }
)

fun SeriesResourceList.toDataDto() = SeriesResourceListDto(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDataDto() }
)