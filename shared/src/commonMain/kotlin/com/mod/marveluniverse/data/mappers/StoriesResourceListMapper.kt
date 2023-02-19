package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.StoriesResourceListDto
import com.mod.marveluniverse.domain.entites.StoriesResourceList

fun StoriesResourceListDto.toDomainEntity() = StoriesResourceList(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDomainEntity() }
)

fun StoriesResourceList.toDataDto() = StoriesResourceListDto(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDataDto() }
)