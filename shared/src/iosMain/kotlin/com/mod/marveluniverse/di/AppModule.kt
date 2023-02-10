package com.mod.marveluniverse.di

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import com.mod.marveluniverse.data.data_sources.SqlDelightRequestDataSource
import com.mod.marveluniverse.data.config.DatabaseDriverFactory
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.RequestDataSource
import com.mod.marveluniverse.domain.entites.EntityType
import com.mod.marveluniverse.domain.entites.RequestType
import database.RequestEntity

class AppModule {
    private val appDatabase: MarvelUniverseDatabase by lazy {
        MarvelUniverseDatabase(
            driver = DatabaseDriverFactory().create(),
            requestEntityAdapter = RequestEntity.Adapter(
                idAdapter = IntColumnAdapter,
                typeAdapter = EnumColumnAdapter<RequestType>(),
                entityAdapter = EnumColumnAdapter<EntityType>(),
                entityIdAdapter = IntColumnAdapter,
                totalResultsAdapter = IntColumnAdapter,
                offsetAdapter = IntColumnAdapter,
                createdAtAdapter = IntColumnAdapter,
                updatedAtAdapter = IntColumnAdapter,
            )
        )
    }

    val requestDataSource: RequestDataSource by lazy {
        SqlDelightRequestDataSource(
            db = appDatabase
        )
    }
}