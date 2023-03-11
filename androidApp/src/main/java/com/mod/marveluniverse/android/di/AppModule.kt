package com.mod.marveluniverse.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.mod.marveluniverse.data.config.DatabaseDriverFactory
import com.mod.marveluniverse.data.config.HttpClientFactory
import com.mod.marveluniverse.data.data_sources.local.*
import com.mod.marveluniverse.data.data_sources.remote.*
import com.mod.marveluniverse.data.repositories.*
import com.mod.marveluniverse.di.SharedAppModule
import com.mod.marveluniverse.domain.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideComicRemoteDataSource(httpClient: HttpClient): ComicRemoteDataSource {
        return ComicRemoteDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideCharacterRemoteDataSource(httpClient: HttpClient): CharacterRemoteDataSource {
        return CharacterRemoteDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideCreatorRemoteDataSource(httpClient: HttpClient): CreatorRemoteDataSource {
        return CreatorRemoteDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideEventRemoteDataSource(httpClient: HttpClient): EventRemoteDataSource {
        return EventRemoteDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideSeriesRemoteDataSource(httpClient: HttpClient): SeriesRemoteDataSource {
        return SeriesRemoteDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideStoryRemoteDataSource(httpClient: HttpClient): StoryRemoteDataSource {
        return StoryRemoteDataSourceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideRequestLocalDataSource(driver: SqlDriver): RequestLocalDataSource {
        return RequestLocalDataSourceImpl(SharedAppModule.provideAppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideComicLocalDataSource(driver: SqlDriver): ComicLocalDataSource {
        return ComicLocalDataSourceImpl(SharedAppModule.provideAppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideCharacterLocalDataSource(driver: SqlDriver): CharacterLocalDataSource {
        return CharacterLocalDataSourceImpl(SharedAppModule.provideAppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideCreatorLocalDataSource(driver: SqlDriver): CreatorLocalDataSource {
        return CreatorLocalDataSourceImpl(SharedAppModule.provideAppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideEventLocalDataSource(driver: SqlDriver): EventLocalDataSource {
        return EventLocalDataSourceImpl(SharedAppModule.provideAppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideSeriesLocalDataSource(driver: SqlDriver): SeriesLocalDataSource {
        return SeriesLocalDataSourceImpl(SharedAppModule.provideAppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideStoryLocalDataSource(driver: SqlDriver): StoryLocalDataSource {
        return StoryLocalDataSourceImpl(SharedAppModule.provideAppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideComicRepository(
        requestLocalDataSource: RequestLocalDataSource,
        comicRemoteDataSource: ComicRemoteDataSource,
        comicLocalDataSource: ComicLocalDataSource
    ): ComicRepository {
        return ComicRepositoryImpl(
            requestLocalDataSource, comicLocalDataSource, comicRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(
        requestLocalDataSource: RequestLocalDataSource,
        characterRemoteDataSource: CharacterRemoteDataSource,
        characterLocalDataSource: CharacterLocalDataSource
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            requestLocalDataSource, characterLocalDataSource, characterRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCreatorRepository(
        requestLocalDataSource: RequestLocalDataSource,
        creatorRemoteDataSource: CreatorRemoteDataSource,
        creatorLocalDataSource: CreatorLocalDataSource
    ): CreatorRepository {
        return CreatorRepositoryImpl(
            requestLocalDataSource, creatorLocalDataSource, creatorRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        requestLocalDataSource: RequestLocalDataSource,
        eventRemoteDataSource: EventRemoteDataSource,
        eventLocalDataSource: EventLocalDataSource
    ): EventRepository {
        return EventRepositoryImpl(
            requestLocalDataSource, eventLocalDataSource, eventRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideSeriesRepository(
        requestLocalDataSource: RequestLocalDataSource,
        seriesRemoteDataSource: SeriesRemoteDataSource,
        seriesLocalDataSource: SeriesLocalDataSource
    ): SeriesRepository {
        return SeriesRepositoryImpl(
            requestLocalDataSource, seriesLocalDataSource, seriesRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideStoryRepository(
        requestLocalDataSource: RequestLocalDataSource,
        storyRemoteDataSource: StoryRemoteDataSource,
        storyLocalDataSource: StoryLocalDataSource
    ): StoryRepository {
        return StoryRepositoryImpl(
            requestLocalDataSource, storyLocalDataSource, storyRemoteDataSource
        )
    }
}