package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.domain.entites.Character
import database.character.CharacterEntity
import database.character.CharacterResourceEntity

fun CharacterEntity.toDomainEntity() = Character(
    id = id,
    name = name,
    description = description,
    modified = modified,
    urls = urls.map { it.toDomainEntity() },
    thumbnail = thumbnail.toDomainEntity(),
    comics = comics.toDomainEntity(),
    stories = stories.toDomainEntity(),
    events = events.toDomainEntity(),
    series = series.toDomainEntity()
)

fun CharacterResourceEntity.toDomainEntity() = Character(
    id = id,
    name = name,
    description = description,
    modified = modified,
    urls = urls.map { it.toDomainEntity() },
    thumbnail = thumbnail.toDomainEntity(),
    comics = comics.toDomainEntity(),
    stories = stories.toDomainEntity(),
    events = events.toDomainEntity(),
    series = series.toDomainEntity()
)