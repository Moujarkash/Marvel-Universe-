package com.mod.marveluniverse.data.data_sources.local

import app.cash.sqldelight.coroutines.asFlow
import com.mod.marveluniverse.data.mappers.toDataDto
import com.mod.marveluniverse.data.mappers.toDomainEntity
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.local.StoryLocalDataSource
import com.mod.marveluniverse.domain.entites.*
import com.mod.marveluniverse.domain.utils.flows.CommonFlow
import com.mod.marveluniverse.domain.utils.flows.toCommonFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class StoryLocalDataSourceImp(
    db: MarvelUniverseDatabase
): StoryLocalDataSource {
    private val storyQueries = db.storyQueries
    private val storyResourceQueries = db.story_resourceQueries

    override fun getStories(): CommonFlow<List<Story>> {
        return storyQueries
            .getStories()
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun getStoryById(id: Int): Story {
        return storyQueries
            .getStoryById(id)
            .executeAsOne()
            .toDomainEntity()
    }

    override fun getStoriesByResource(
        resourceType: ResourceType,
        resourceId: Int
    ): CommonFlow<List<Story>> {
        return storyResourceQueries
            .getStoriesResourceByType(resourceType, resourceId)
            .asFlow()
            .map { query ->
                query.executeAsList()
            }
            .map { comics ->
                comics.map { it.toDomainEntity() }
            }
            .toCommonFlow()
    }

    override fun insertStory(
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
    ) {
        storyQueries
            .insertStory(
                id = id,
                title = title,
                description = description,
                type = type,
                modified = modified,
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                series = series.toDataDto(),
                events = events.toDataDto(),
                characters = characters.toDataDto(),
                creators = creators.toDataDto(),
                originalIssue = originalIssue?.toDataDto()
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
        thumbnail: Image,
        comics: ComicsResourceList,
        series: SeriesResourceList,
        events: EventsResourceList,
        characters: CharactersResourceList,
        creators: CreatorsResourceList,
        originalIssue: ComicSummary?
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
                thumbnail = thumbnail.toDataDto(),
                comics = comics.toDataDto(),
                series = series.toDataDto(),
                events = events.toDataDto(),
                characters = characters.toDataDto(),
                creators = creators.toDataDto(),
                originalIssue = originalIssue?.toDataDto()
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