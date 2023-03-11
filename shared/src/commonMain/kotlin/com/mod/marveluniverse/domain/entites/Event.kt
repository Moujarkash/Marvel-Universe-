package com.mod.marveluniverse.domain.entites

import kotlinx.datetime.LocalDateTime

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val urls: List<Url>,
    val modified: LocalDateTime,
    val start: LocalDateTime? = null,
    val end: LocalDateTime? = null,
    val thumbnail: Image,
    val comics: ComicsResourceList,
    val stories: StoriesResourceList,
    val series: SeriesResourceList,
    val characters: CharactersResourceList,
    val creators: CreatorsResourceList,
    val next: EventSummary?,
    val previous: EventSummary?
)
