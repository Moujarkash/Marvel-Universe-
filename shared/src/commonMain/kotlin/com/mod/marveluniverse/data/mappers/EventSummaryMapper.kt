package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.EventSummaryDto
import com.mod.marveluniverse.data.utils.ResourceHelper
import com.mod.marveluniverse.domain.entites.EventSummary

fun EventSummaryDto.toDomainEntity() = EventSummary(
    id = ResourceHelper.getResourceIdFromResourceUrl(resourceURI),
    resourceURI = resourceURI,
    name = name
)

fun EventSummary.toDataDto() = EventSummaryDto(
    resourceURI = resourceURI,
    name = name
)