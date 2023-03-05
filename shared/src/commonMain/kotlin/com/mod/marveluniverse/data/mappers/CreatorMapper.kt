package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.domain.entites.Creator
import database.creator.CreatorEntity
import database.creator.CreatorResourceEntity

fun CreatorEntity.toDomainEntity() = Creator(
    id = remoteId,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    suffix = suffix,
    fullName = fullName,
    modified = modified,
    urls = urls.map { it.toDomainEntity() },
    thumbnail = thumbnail.toDomainEntity(),
    series = series.toDomainEntity(),
    stories = stories.toDomainEntity(),
    comics = comics.toDomainEntity(),
    events = events.toDomainEntity()
)

fun CreatorResourceEntity.toDomainEntity() = Creator(
    id = remoteId,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    suffix = suffix,
    fullName = fullName,
    modified = modified,
    urls = urls.map { it.toDomainEntity() },
    thumbnail = thumbnail.toDomainEntity(),
    series = series.toDomainEntity(),
    stories = stories.toDomainEntity(),
    comics = comics.toDomainEntity(),
    events = events.toDomainEntity()
)