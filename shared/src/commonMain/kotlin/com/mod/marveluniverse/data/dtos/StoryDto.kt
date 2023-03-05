@file:UseSerializers(LocalDateTimeAsStringSerializer::class)

package com.mod.marveluniverse.data.dtos

import com.mod.marveluniverse.data.serializers.LocalDateTimeAsStringSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class StoryDto(
    val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val modified: LocalDateTime,
    val thumbnail: ImageDto,
    val comics: ComicsResourceListDto,
    val series: SeriesResourceListDto,
    val events: EventsResourceListDto,
    val characters: CharactersResourceListDto,
    val creators: CreatorsResourceListDto,
    val originalIssue: ComicSummaryDto?
)
