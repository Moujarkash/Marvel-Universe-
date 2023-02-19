package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.CreatorsResourceListDto
import com.mod.marveluniverse.domain.entites.CreatorsResourceList

fun CreatorsResourceListDto.toDomainEntity() = CreatorsResourceList(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDomainEntity() }
)

fun CreatorsResourceList.toDataDto() = CreatorsResourceListDto(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDataDto() }
)