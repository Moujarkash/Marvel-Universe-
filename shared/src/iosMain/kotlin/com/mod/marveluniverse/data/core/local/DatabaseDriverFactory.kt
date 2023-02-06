package com.mod.marveluniverse.data.core.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.mod.marveluniverse.database.MarvelUniverseDatabase


actual class DatabaseDriverFactory{
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(MarvelUniverseDatabase.Schema, "MarvelUniverse.db")
    }
}