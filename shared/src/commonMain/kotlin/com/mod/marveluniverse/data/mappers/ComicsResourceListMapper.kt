package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.ComicsResourceListDto
import com.mod.marveluniverse.domain.entites.ComicsResourceList

fun ComicsResourceListDto.toDomainEntity() = ComicsResourceList(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDomainEntity() }
)

fun ComicsResourceList.toDataDto() = ComicsResourceListDto(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDataDto() }
)