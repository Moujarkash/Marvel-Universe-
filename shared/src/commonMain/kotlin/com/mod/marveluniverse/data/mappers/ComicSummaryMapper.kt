package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.ComicSummaryDto
import com.mod.marveluniverse.data.utils.ResourceHelper
import com.mod.marveluniverse.domain.entites.ComicSummary

fun ComicSummaryDto.toDomainEntity() = ComicSummary(
    id = ResourceHelper.getResourceIdFromResourceUrl(resourceURI),
    resourceURI = resourceURI,
    name = name
)

fun ComicSummary.toDataDto() = ComicSummaryDto(
    resourceURI = resourceURI,
    name = name
)