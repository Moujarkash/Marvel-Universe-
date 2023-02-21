package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.story.StoryEntity
import database.story.StoryResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

interface StoryLocalDataSource {
    fun getStories(): Flow<List<StoryEntity>>
    fun getStoryById(id: Int): StoryEntity
    fun getStoriesByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<StoryResourceEntity>>

    fun insertStory(
        id: Int,
        title: String,
        description: String,
        type: String,
        modified: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        events: EventsResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        originalIssue: ComicSummaryDto?
    )

    fun insertStoryResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        type: String,
        modified: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        events: EventsResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        originalIssue: ComicSummaryDto?
    )

    fun clearStories()
    fun clearStoriesResource(resourceType: ResourceType, resourceId: Int)
}

class StoryLocalDataSourceImpl(
    db: MarvelUniverseDatabase
) : StoryLocalDataSource {
    private val storyQueries = db.storyQueries
    private val storyResourceQueries = db.story_resourceQueries

    override fun getStories(): Flow<List<StoryEntity>> {
        return storyQueries
            .getStories()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun getStoryById(id: Int): StoryEntity {
        return storyQueries
            .getStoryById(id)
            .executeAsOne()
    }

    override fun getStoriesByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<StoryResourceEntity>> {
        return storyResourceQueries
            .getStoriesResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
    }

    override fun insertStory(
        id: Int,
        title: String,
        description: String,
        type: String,
        modified: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        events: EventsResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        originalIssue: ComicSummaryDto?
    ) {
        storyQueries
            .insertStory(
                id = id,
                title = title,
                description = description,
                type = type,
                modified = modified,
                thumbnail = thumbnail,
                comics = comics,
                series = series,
                events = events,
                characters = characters,
                creators = creators,
                originalIssue = originalIssue
            )
    }

    override fun insertStoryResource(
        resourceType: ResourceType,
        resourceId: Int,
        id: Int,
        title: String,
        description: String,
        type: String,
        modified: LocalDateTime,
        thumbnail: ImageDto,
        comics: ComicsResourceListDto,
        series: SeriesResourceListDto,
        events: EventsResourceListDto,
        characters: CharactersResourceListDto,
        creators: CreatorsResourceListDto,
        originalIssue: ComicSummaryDto?
    ) {
        storyResourceQueries
            .insertStoryResource(
                resourceType = resourceType,
                resourceId = resourceId,
                id = id,
                title = title,
                description = description,
                type = type,
                modified = modified,
                thumbnail = thumbnail,
                comics = comics,
                series = series,
                events = events,
                characters = characters,
                creators = creators,
                originalIssue = originalIssue
            )
    }

    override fun clearStories() {
        storyQueries.clearStories()
    }

    override fun clearStoriesResource(resourceType: ResourceType, resourceId: Int) {
        storyResourceQueries.clearStoriesResource(
            resourceType = resourceType,
            resourceId = resourceId
        )
    }
}