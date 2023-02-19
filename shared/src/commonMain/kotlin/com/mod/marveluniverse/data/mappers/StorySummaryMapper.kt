package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.StorySummaryDto
import com.mod.marveluniverse.data.utils.ResourceHelper
import com.mod.marveluniverse.domain.entites.StorySummary

fun StorySummaryDto.toDomainEntity() = StorySummary(
    id = ResourceHelper.getResourceIdFromResourceUrl(resourceURI),
    resourceURI = resourceURI,
    name = name,
    type = type
)

fun StorySummary.toDataDto() = StorySummaryDto(
    resourceURI = resourceURI,
    name = name,
    type = type
)