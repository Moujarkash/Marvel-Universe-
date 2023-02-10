package com.mod.marveluniverse.domain.entites

import kotlinx.datetime.LocalDateTime

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val urls: List<Url>,
    val modified: LocalDateTime,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val thumbnail: Image,
    val comics: ResourceList<ComicSummary>,
    val stories: ResourceList<StorySummary>,
    val series: ResourceList<SeriesSummary>,
    val characters: ResourceList<CharacterSummary>,
    val creators: ResourceList<CreatorSummary>,
    val next: EventSummary?,
    val previous: EventSummary?
)
