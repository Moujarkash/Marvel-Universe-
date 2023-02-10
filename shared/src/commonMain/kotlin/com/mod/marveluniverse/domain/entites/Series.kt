package com.mod.marveluniverse.domain.entites

import kotlinx.datetime.LocalDateTime

data class Series(
    val id: Int,
    val title: String,
    val description: String,
    val urls: List<Url>,
    val startYear: Int,
    val endYear: Int,
    val rating: String,
    val modified: LocalDateTime,
    val thumbnail: Image,
    val comics: ResourceList<ComicSummary>,
    val stories: ResourceList<StorySummary>,
    val events: ResourceList<EventSummary>,
    val characters: ResourceList<CharacterSummary>,
    val creators: ResourceList<CreatorSummary>,
    val next: SeriesSummary?,
    val previous: SeriesSummary?
)
