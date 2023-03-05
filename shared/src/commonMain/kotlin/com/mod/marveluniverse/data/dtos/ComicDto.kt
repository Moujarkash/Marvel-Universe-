@file:UseSerializers(LocalDateTimeAsStringSerializer::class)

package com.mod.marveluniverse.data.dtos

import com.mod.marveluniverse.data.serializers.LocalDateTimeAsStringSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class ComicDto(
    val id: Int,
    val title: String,
    val description: String = "",
    val modified: LocalDateTime,
    val isbn: String,
    val pageCount: Int,
    val textObjects: List<TextObjectDto>,
    val urls: List<UrlDto>,
    val series: SeriesSummaryDto,
    val variants: List<ComicSummaryDto>,
    val dates: List<ComicDateDto>,
    val prices: List<ComicPriceDto>,
    val thumbnail: ImageDto,
    val images: List<ImageDto>,
    val creators: CreatorsResourceListDto,
    val characters: CharactersResourceListDto,
    val stories: StoriesResourceListDto,
    val events: EventsResourceListDto
)