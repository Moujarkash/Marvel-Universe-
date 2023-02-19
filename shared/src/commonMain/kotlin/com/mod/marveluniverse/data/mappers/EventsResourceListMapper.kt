package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.EventsResourceListDto
import com.mod.marveluniverse.domain.entites.EventsResourceList

fun EventsResourceListDto.toDomainEntity() = EventsResourceList(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDomainEntity() }
)

fun EventsResourceList.toDataDto() = EventsResourceListDto(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDataDto() }
)