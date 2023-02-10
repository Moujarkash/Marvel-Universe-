package com.mod.marveluniverse.data.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ComicDto(
    val id: Int,
    val title: String,
    val description: String,
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
    val creators: ResourceListDto<CreatorSummaryDto>,
    val characters: ResourceListDto<CharacterSummaryDto>,
    val stories: ResourceListDto<StorySummaryDto>,
    val events: ResourceListDto<EventSummaryDto>
)