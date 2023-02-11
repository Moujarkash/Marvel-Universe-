package com.mod.marveluniverse.data.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val description: String,
    val modified: LocalDateTime,
    val urls: List<UrlDto>,
    val thumbnail: ImageDto,
    val comics: ResourceListDto<ComicSummaryDto>,
    val stories: ResourceListDto<StorySummaryDto>,
    val events: ResourceListDto<EventSummaryDto>,
    val series: ResourceListDto<SeriesSummaryDto>
)
