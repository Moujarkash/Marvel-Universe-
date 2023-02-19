package com.mod.marveluniverse.data.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SeriesDto(
    val id: Int,
    val title: String,
    val description: String,
    val urls: List<UrlDto>,
    val startYear: Int,
    val endYear: Int,
    val rating: String,
    val modified: LocalDateTime,
    val thumbnail: ImageDto,
    val comics: ComicsResourceListDto,
    val stories: StoriesResourceListDto,
    val events: EventsResourceListDto,
    val characters: CharactersResourceListDto,
    val creators: CreatorsResourceListDto,
    val next: SeriesSummaryDto?,
    val previous: SeriesSummaryDto?
)
