package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.CharactersResourceListDto
import com.mod.marveluniverse.domain.entites.CharactersResourceList

fun CharactersResourceListDto.toDomainEntity() = CharactersResourceList(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDomainEntity() }
)

fun CharactersResourceList.toDataDto() = CharactersResourceListDto(
    available = available,
    returned = returned,
    collectionURI = collectionURI,
    items = items.map { it.toDataDto() }
)