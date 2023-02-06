package com.mod.marveluniverse.data.core.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mod.marveluniverse.database.MarvelUniverseDatabase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(MarvelUniverseDatabase.Schema, context, "MarvelUniverse.db")
    }
}