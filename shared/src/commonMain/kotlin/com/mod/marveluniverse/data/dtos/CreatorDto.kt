package com.mod.marveluniverse.data.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class CreatorDto(
    val id: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val suffix: String,
    val fullName: String,
    val modified: LocalDateTime,
    val urls: List<UrlDto>,
    val thumbnail: ImageDto,
    val series: ResourceListDto<SeriesSummaryDto>,
    val stories: ResourceListDto<StorySummaryDto>,
    val comics: ResourceListDto<ComicSummaryDto>,
    val events: ResourceListDto<EventSummaryDto>
)
