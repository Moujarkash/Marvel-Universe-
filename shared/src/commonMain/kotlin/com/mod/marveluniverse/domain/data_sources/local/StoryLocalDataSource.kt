package com.mod.marveluniverse.domain.data_sources.local

import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import kotlinx.datetime.LocalDateTime

interface StoryLocalDataSource {
    fun getStories(): CommonFlow<List<Story>>
    fun getStoryById(id: Int): Story
    fun getStoriesByResource(resourceType: ResourceType, resourceId: Int): CommonFlow<List<Story>>
    fun insertStory(
        id: Int,
        title: String,
        description: String,
        type: String,
        modified: LocalDateTime,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        events: EventsResourceList,
        characters: CharactersResourceList,
        creators: CreatorsResourceList,
        originalIssue: ComicSummary?
    )
    fun insertStoryResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        type: String,
        modified: LocalDateTime,
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        events: EventsResourceList,
        characters: CharactersResourceList,
        creators: CreatorsResourceList,
        originalIssue: ComicSummary?
    )
    fun clearStories()
    fun clearStoriesResource(resourceType: ResourceType, resourceId: Int)
}