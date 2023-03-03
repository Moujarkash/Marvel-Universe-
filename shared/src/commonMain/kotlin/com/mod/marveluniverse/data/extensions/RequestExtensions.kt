package com.mod.marveluniverse.data.extensions

import database.RequestEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun RequestEntity.isExpired(): Boolean =
    Clock.System.now().minus(updatedAt.toInstant(TimeZone.currentSystemDefault())).inWholeHours > 24
