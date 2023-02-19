package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.CharacterSummaryDto
import com.mod.marveluniverse.data.utils.ResourceHelper
import com.mod.marveluniverse.domain.entites.CharacterSummary

fun CharacterSummaryDto.toDomainEntity() = CharacterSummary(
    id = ResourceHelper.getResourceIdFromResourceUrl(resourceURI),
    resourceURI = resourceURI,
    name = name
)

fun CharacterSummary.toDataDto() = CharacterSummaryDto(
    resourceURI = resourceURI,
    name = name
)