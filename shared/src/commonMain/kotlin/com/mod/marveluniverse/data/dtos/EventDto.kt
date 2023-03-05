@file:UseSerializers(LocalDateTimeAsStringSerializer::class)

package com.mod.marveluniverse.data.dtos

import com.mod.marveluniverse.data.serializers.LocalDateTimeAsStringSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

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
