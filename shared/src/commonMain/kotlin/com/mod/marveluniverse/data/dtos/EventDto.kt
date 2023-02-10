package com.mod.marveluniverse.data.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: Int,
    val title: String,
    val description: String,
    val urls: List<UrlDto>,
    val modified: LocalDateTime,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val thumbnail: ImageDto,
    val comics: ResourceListDto<ComicSummaryDto>,
    val stories: ResourceListDto<StorySummaryDto>,
    val series: ResourceListDto<SeriesSummaryDto>,
    val characters: ResourceListDto<CharacterSummaryDto>,
    val creators: ResourceListDto<CreatorSummaryDto>,
    val next: EventSummaryDto?,
    val previous: EventSummaryDto?
)
