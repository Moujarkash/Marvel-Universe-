package com.mod.marveluniverse.domain.entites

import kotlinx.datetime.LocalDateTime

data class Story(
    val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val modified: LocalDateTime,
    val thumbnail: Image,
    val comics: ComicsResourceList,
    val series: SeriesResourceList,
    val events: EventsResourceList,
    val characters: CharactersResourceList,
    val creators: CreatorsResourceList,
    val originalIssue: ComicSummary?
)
