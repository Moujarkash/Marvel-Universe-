package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.SeriesSummaryDto
import com.mod.marveluniverse.data.utils.ResourceHelper
import com.mod.marveluniverse.domain.entites.SeriesSummary

fun SeriesSummaryDto.toDomainEntity() = SeriesSummary(
    id = ResourceHelper.getResourceIdFromResourceUrl(resourceURI),
    resourceURI = resourceURI,
    name = name
)

fun SeriesSummary.toDataDto() = SeriesSummaryDto(
    resourceURI = resourceURI,
    name = name
)