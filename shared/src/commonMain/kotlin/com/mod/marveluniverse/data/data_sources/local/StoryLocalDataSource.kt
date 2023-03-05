package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.dtos.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.entites.ResourceType
import database.story.StoryEntity
import database.story.StoryResourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface StoryLocalDataSource {
    fun getStories(): Flow<List<StoryEntity>>
    fun getStoryById(id: Int): StoryEntity
    fun getStoriesByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): Flow<List<StoryResourceEntity>>

    fun insertStories(
        stories: List<StoryDto>
    )

    fun insertStoriesResource(
        resourceType: ResourceType,
        resourceId: Int,
        stories: List<StoryDto>
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
            .getStoryByRemoteId(id)
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

    override fun insertStories(
        stories: List<StoryDto>
    ) {
        storyQueries.transaction {
            stories.forEach {
                storyQueries
                    .insertStory(
                        id = null,
                        remoteId = it.id,
                        title = it.title,
                        description = it.description,
                        type = it.type,
                        modified = it.modified,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        series = it.series,
                        events = it.events,
                        characters = it.characters,
                        creators = it.creators,
                        originalIssue = it.originalIssue
                    )
            }
        }
    }

    override fun insertStoriesResource(
        resourceType: ResourceType,
        resourceId: Int,
        stories: List<StoryDto>
    ) {
        storyResourceQueries.transaction {
            stories.forEach {
                storyResourceQueries
                    .insertStoryResource(
                        id = null,
                        resourceType = resourceType,
                        resourceId = resourceId,
                        remoteId = it.id,
                        title = it.title,
                        description = it.description,
                        type = it.type,
                        modified = it.modified,
                        thumbnail = it.thumbnail,
                        comics = it.comics,
                        series = it.series,
                        events = it.events,
                        characters = it.characters,
                        creators = it.creators,
                        originalIssue = it.originalIssue
                    )
            }
        }
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