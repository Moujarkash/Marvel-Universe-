package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.CreatorSummaryDto
import com.mod.marveluniverse.data.utils.ResourceHelper
import com.mod.marveluniverse.domain.entites.CreatorSummary

fun CreatorSummaryDto.toDomainEntity() = CreatorSummary(
    id = ResourceHelper.getResourceIdFromResourceUrl(resourceURI),
    resourceURI = resourceURI,
    name = name,
    role = role
)

fun CreatorSummary.toDataDto() = CreatorSummaryDto(
    resourceURI = resourceURI,
    name = name,
    role = role
)