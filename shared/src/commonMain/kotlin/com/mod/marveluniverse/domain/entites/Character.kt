package com.mod.marveluniverse.domain.entites

import kotlinx.datetime.LocalDateTime

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: LocalDateTime,
    val urls: List<Url>,
    val thumbnail: Image,
    val comics: ComicsResourceList,
    val stories: StoriesResourceList,
    val events: EventsResourceList,
    val series: SeriesResourceList
)
