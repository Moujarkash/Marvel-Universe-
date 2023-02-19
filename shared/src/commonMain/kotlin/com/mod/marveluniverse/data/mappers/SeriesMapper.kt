package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.domain.entites.Series
import database.series.SeriesEntity
import database.series.SeriesResourceEntity

fun SeriesEntity.toDomainEntity() = Series(
    id = id,
    title = title,
    description = description,
    urls = urls.map { it.toDomainEntity() },
    startYear = startYear,
    endYear = endYear,
    rating = rating,
    modified = modified,
    thumbnail = thumbnail.toDomainEntity(),
    comics = comics.toDomainEntity(),
    stories = stories.toDomainEntity(),
    characters = characters.toDomainEntity(),
    creators = creators.toDomainEntity(),
    events = events.toDomainEntity(),
    next = next?.toDomainEntity(),
    previous = previous?.toDomainEntity()
)

fun SeriesResourceEntity.toDomainEntity() = Series(
    id = id,
    title = title,
    description = description,
    urls = urls.map { it.toDomainEntity() },
    startYear = startYear,
    endYear = endYear,
    rating = rating,
    modified = modified,
    thumbnail = thumbnail.toDomainEntity(),
    comics = comics.toDomainEntity(),
    stories = stories.toDomainEntity(),
    characters = characters.toDomainEntity(),
    creators = creators.toDomainEntity(),
    events = events.toDomainEntity(),
    next = next?.toDomainEntity(),
    previous = previous?.toDomainEntity()
)