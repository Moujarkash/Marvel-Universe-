package com.mod.marveluniverse.domain.entites

import kotlinx.datetime.LocalDateTime

data class Creator(
    val id: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val suffix: String,
    val fullName: String,
    val modified: LocalDateTime,
    val urls: List<Url>,
    val thumbnail: Image,
    val series: ResourceList<SeriesSummary>,
    val stories: ResourceList<StorySummary>,
    val comics: ResourceList<ComicSummary>,
    val events: ResourceList<EventSummary>
)