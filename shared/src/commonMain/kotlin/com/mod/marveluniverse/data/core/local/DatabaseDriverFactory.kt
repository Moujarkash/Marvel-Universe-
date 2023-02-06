package com.mod.marveluniverse.data.core.local

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}