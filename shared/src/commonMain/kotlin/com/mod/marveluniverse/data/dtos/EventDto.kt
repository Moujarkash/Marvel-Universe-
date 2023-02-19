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
    val comics: ComicsResourceListDto,
    val stories: StoriesResourceListDto,
    val series: SeriesResourceListDto,
    val characters: CharactersResourceListDto,
    val creators: CreatorsResourceListDto,
    val next: EventSummaryDto?,
    val previous: EventSummaryDto?
)
