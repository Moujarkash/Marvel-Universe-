package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.domain.entites.Story
import database.story.StoryEntity
import database.story.StoryResourceEntity

fun StoryEntity.toDomainEntity() = Story(
    id = id,
    title = title,
    description = description,
    type = type,
    modified = modified,
    thumbnail = thumbnail.toDomainEntity(),
    comics = comics.toDomainEntity(),
    series = series.toDomainEntity(),
    events = events.toDomainEntity(),
    characters = characters.toDomainEntity(),
    creators = creators.toDomainEntity(),
    originalIssue = originalIssue?.toDomainEntity()
)

fun StoryResourceEntity.toDomainEntity() = Story(
    id = id,
    title = title,
    description = description,
    type = type,
    modified = modified,
    thumbnail = thumbnail.toDomainEntity(),
    comics = comics.toDomainEntity(),
    series = series.toDomainEntity(),
    events = events.toDomainEntity(),
    characters = characters.toDomainEntity(),
    creators = creators.toDomainEntity(),
    originalIssue = originalIssue?.toDomainEntity()
)