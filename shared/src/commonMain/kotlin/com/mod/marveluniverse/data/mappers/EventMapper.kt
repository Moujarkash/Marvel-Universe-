package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.domain.entites.Event
import database.event.EventEntity
import database.event.EventResourceEntity

fun EventEntity.toDomainEntity() = Event(
    id = remoteId,
    title = title,
    description = description,
    urls = urls.map { it.toDomainEntity() },
    modified = modified,
    start = start,
    end = end,
    thumbnail = thumbnail.toDomainEntity(),
    comics = comics.toDomainEntity(),
    series = series.toDomainEntity(),
    stories = stories.toDomainEntity(),
    creators = creators.toDomainEntity(),
    characters = characters.toDomainEntity(),
    next = next?.toDomainEntity(),
    previous = previous?.toDomainEntity()
)

fun EventResourceEntity.toDomainEntity() = Event(
    id = remoteId,
    title = title,
    description = description,
    urls = urls.map { it.toDomainEntity() },
    modified = modified,
    start = start,
    end = end,
    thumbnail = thumbnail.toDomainEntity(),
    comics = comics.toDomainEntity(),
    series = series.toDomainEntity(),
    stories = stories.toDomainEntity(),
    creators = creators.toDomainEntity(),
    characters = characters.toDomainEntity(),
    next = next?.toDomainEntity(),
    previous = previous?.toDomainEntity()
)