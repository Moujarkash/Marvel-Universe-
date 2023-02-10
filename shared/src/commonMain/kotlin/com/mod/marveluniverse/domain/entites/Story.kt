package com.mod.marveluniverse.domain.entites

import kotlinx.datetime.LocalDateTime

data class Story(
    val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val modified: LocalDateTime,
    val thumbnail: Image,
    val comics: ResourceList<ComicSummary>,
    val series: ResourceList<SeriesSummary>,
    val events: ResourceList<EventSummary>,
    val characters: ResourceList<CharacterSummary>,
    val creators: ResourceList<CreatorSummary>,
    val originalIssue: ComicSummary?
)
