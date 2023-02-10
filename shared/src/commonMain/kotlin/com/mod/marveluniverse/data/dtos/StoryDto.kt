package com.mod.marveluniverse.data.dtos

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: Int,
    val title: String,
    val description: String,
    val type: String,
    val modified: LocalDateTime,
    val thumbnail: ImageDto,
    val comics: ResourceListDto<ComicSummaryDto>,
    val series: ResourceListDto<SeriesSummaryDto>,
    val events: ResourceListDto<EventSummaryDto>,
    val characters: ResourceListDto<CharacterSummaryDto>,
    val creators: ResourceListDto<CreatorSummaryDto>,
    val originalIssue: ComicSummaryDto?
)
