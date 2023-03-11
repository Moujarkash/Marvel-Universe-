@file:UseSerializers(LocalDateTimeAsStringSerializer::class)

package com.mod.marveluniverse.data.dtos

import com.mod.marveluniverse.data.serializers.LocalDateTimeAsStringSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val description: String,
    val modified: LocalDateTime,
    val urls: List<UrlDto>,
    val thumbnail: ImageDto,
    val comics: ComicsResourceListDto,
    val stories: StoriesResourceListDto,
    val events: EventsResourceListDto,
    val series: SeriesResourceListDto
)
