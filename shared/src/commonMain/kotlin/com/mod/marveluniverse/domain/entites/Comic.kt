package com.mod.marveluniverse.domain.entites

import kotlinx.datetime.LocalDateTime

data class Comic(
    val id: Int,
    val title: String,
    val description: String,
    val modified: LocalDateTime,
    val isbn: String,
    val pageCount: Int,
    val textObjects: List<TextObject>,
    val urls: List<Url>,
    val series: SeriesSummary,
    val variants: List<ComicSummary>,
    val dates: List<ComicDate>,
    val prices: List<ComicPrice>,
    val thumbnail: Image,
    val images: List<Image>,
    val creators: ResourceList<CreatorSummary>,
    val characters: ResourceList<CharacterSummary>,
    val stories: ResourceList<StorySummary>,
    val events: ResourceList<EventSummary>
)
