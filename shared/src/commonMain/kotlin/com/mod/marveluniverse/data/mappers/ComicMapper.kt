package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.domain.entites.Comic
import database.comic.ComicEntity
import database.comic.ComicResourceEntity

fun ComicEntity.toDomainEntity() = Comic(
    id = remoteId,
    title = title,
    description = description,
    modified = modified,
    isbn = isbn,
    pageCount = pageCount,
    textObjects = textObjects.map { it.toDomainEntity() },
    urls = urls.map { it.toDomainEntity() },
    series = series.toDomainEntity(),
    variants = variants.map { it.toDomainEntity() },
    dates = dates.map { it.toDomainEntity() },
    prices = prices.map { it.toDomainEntity() },
    thumbnail = thumbnail.toDomainEntity(),
    images = images.map { it.toDomainEntity() },
    creators = creators.toDomainEntity(),
    characters = characters.toDomainEntity(),
    stories = stories.toDomainEntity(),
    events = events.toDomainEntity()
)

fun ComicResourceEntity.toDomainEntity() = Comic(
    id = remoteId,
    title = title,
    description = description,
    modified = modified,
    isbn = isbn,
    pageCount = pageCount,
    textObjects = textObjects.map { it.toDomainEntity() },
    urls = urls.map { it.toDomainEntity() },
    series = series.toDomainEntity(),
    variants = variants.map { it.toDomainEntity() },
    dates = dates.map { it.toDomainEntity() },
    prices = prices.map { it.toDomainEntity() },
    thumbnail = thumbnail.toDomainEntity(),
    images = images.map { it.toDomainEntity() },
    creators = creators.toDomainEntity(),
    characters = characters.toDomainEntity(),
    stories = stories.toDomainEntity(),
    events = events.toDomainEntity()
)