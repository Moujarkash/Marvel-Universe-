package com.mod.marveluniverse.domain.data_sources.local

import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import kotlinx.datetime.LocalDateTime

interface CreatorLocalDataSource {
    fun getCreators(): CommonFlow<List<Creator>>
    fun getCreatorById(id: Int): Creator
    fun getCreatorsByResource(resourceType: ResourceType, resourceId: Int): CommonFlow<List<Creator>>
    fun insertCreator(
        id: Int,
        firstName: String,
        middleName: String,
        lastName: String,
        suffix: String,
        fullName: String,
        modified: LocalDateTime,
        urls: List<Url>,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList
    )
    fun insertCreatorResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        firstName: String,
        middleName: String,
        lastName: String,
        suffix: String,
        fullName: String,
        modified: LocalDateTime,
        urls: List<Url>,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        stories: StoriesResourceList,
        events: EventsResourceList
    )
    fun clearCreators()
    fun clearCreatorsResource(resourceType: ResourceType, resourceId: Int)
}