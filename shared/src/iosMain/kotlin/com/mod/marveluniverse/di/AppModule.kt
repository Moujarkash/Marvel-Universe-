package com.mod.marveluniverse.di

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import com.mod.marveluniverse.data.data_sources.SqlDelightRequestDataSource
import com.mod.marveluniverse.data.config.DatabaseDriverFactory
import com.mod.marveluniverse.data.db_adapters.*
import com.mod.marveluniverse.database.MarvelUniverseDatabase
import com.mod.marveluniverse.domain.data_sources.RequestDataSource
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.RequestType
import database.RequestEntity
import database.character.CharacterEntity
import database.character.CharacterResourceEntity
import database.comic.ComicEntity
import database.comic.ComicResourceEntity
import database.creator.CreatorEntity
import database.creator.CreatorResourceEntity
import database.event.EventEntity
import database.event.EventResourceEntity
import database.series.SeriesEntity
import database.series.SeriesResourceEntity
import database.story.StoryEntity
import database.story.StoryResourceEntity

class AppModule {
    private val appDatabase: MarvelUniverseDatabase by lazy {
        MarvelUniverseDatabase(
            driver = DatabaseDriverFactory().create(),
            requestEntityAdapter = RequestEntity.Adapter(
                idAdapter = IntColumnAdapter,
                typeAdapter = EnumColumnAdapter<RequestType>(),
                resourceTypeAdapter = EnumColumnAdapter<ResourceType>(),
                resourceIdAdapter = IntColumnAdapter,
                totalResultsAdapter = IntColumnAdapter,
                offsetAdapter = IntColumnAdapter,
                createdAtAdapter = LocalDateTimeAdapter(),
                updatedAtAdapter = LocalDateTimeAdapter(),
            ),
            comicEntityAdapter = ComicEntity.Adapter(
                idAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                pageCountAdapter = IntColumnAdapter,
                textObjectsAdapter = TextObjectsAdapter(),
                urlsAdapter = UrlsAdapter(),
                seriesAdapter = SeriesSummaryAdapter(),
                variantsAdapter = ComicSummariesAdapter(),
                datesAdapter = ComicDatesAdapter(),
                pricesAdapter = ComicPricesAdapter(),
                thumbnailAdapter = ImageAdapter(),
                imagesAdapter = ImagesAdapter(),
                creatorsAdapter = CreatorsResourceListAdapter(),
                charactersAdapter = CharactersResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter()
            ),
            comicResourceEntityAdapter = ComicResourceEntity.Adapter(
                resourceTypeAdapter = EnumColumnAdapter<ResourceType>(),
                resourceIdAdapter = IntColumnAdapter,
                idAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                pageCountAdapter = IntColumnAdapter,
                textObjectsAdapter = TextObjectsAdapter(),
                urlsAdapter = UrlsAdapter(),
                seriesAdapter = SeriesSummaryAdapter(),
                variantsAdapter = ComicSummariesAdapter(),
                datesAdapter = ComicDatesAdapter(),
                pricesAdapter = ComicPricesAdapter(),
                thumbnailAdapter = ImageAdapter(),
                imagesAdapter = ImagesAdapter(),
                creatorsAdapter = CreatorsResourceListAdapter(),
                charactersAdapter = CharactersResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter()
            ),
            characterEntityAdapter = CharacterEntity.Adapter(
                idAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                urlsAdapter = UrlsAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                seriesAdapter = SeriesResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter()
            ),
            characterResourceEntityAdapter = CharacterResourceEntity.Adapter(
                resourceTypeAdapter = EnumColumnAdapter<ResourceType>(),
                resourceIdAdapter = IntColumnAdapter,
                idAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                urlsAdapter = UrlsAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                seriesAdapter = SeriesResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter()
            ),
            creatorEntityAdapter = CreatorEntity.Adapter(
                idAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                urlsAdapter = UrlsAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                seriesAdapter = SeriesResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter()
            ),
            creatorResourceEntityAdapter = CreatorResourceEntity.Adapter(
                resourceTypeAdapter = EnumColumnAdapter<ResourceType>(),
                resourceIdAdapter = IntColumnAdapter,
                idAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                urlsAdapter = UrlsAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                seriesAdapter = SeriesResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter()
            ),
            eventEntityAdapter = EventEntity.Adapter(
                idAdapter = IntColumnAdapter,
                urlsAdapter = UrlsAdapter(),
                modifiedAdapter = LocalDateTimeAdapter(),
                startAdapter = LocalDateTimeAdapter(),
                endAdapter = LocalDateTimeAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                seriesAdapter = SeriesResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                charactersAdapter = CharactersResourceListAdapter(),
                creatorsAdapter = CreatorsResourceListAdapter(),
                nextAdapter = EventSummaryAdapter(),
                previousAdapter = EventSummaryAdapter()
            ),
            eventResourceEntityAdapter = EventResourceEntity.Adapter(
                resourceTypeAdapter = EnumColumnAdapter<ResourceType>(),
                resourceIdAdapter = IntColumnAdapter,
                idAdapter = IntColumnAdapter,
                urlsAdapter = UrlsAdapter(),
                modifiedAdapter = LocalDateTimeAdapter(),
                startAdapter = LocalDateTimeAdapter(),
                endAdapter = LocalDateTimeAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                seriesAdapter = SeriesResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                charactersAdapter = CharactersResourceListAdapter(),
                creatorsAdapter = CreatorsResourceListAdapter(),
                nextAdapter = EventSummaryAdapter(),
                previousAdapter = EventSummaryAdapter()
            ),
            seriesEntityAdapter = SeriesEntity.Adapter(
                idAdapter = IntColumnAdapter,
                startYearAdapter = IntColumnAdapter,
                endYearAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                urlsAdapter = UrlsAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                charactersAdapter = CharactersResourceListAdapter(),
                creatorsAdapter = CreatorsResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter(),
                nextAdapter = SeriesSummaryAdapter(),
                previousAdapter = SeriesSummaryAdapter()
            ),
            seriesResourceEntityAdapter = SeriesResourceEntity.Adapter(
                resourceTypeAdapter = EnumColumnAdapter<ResourceType>(),
                resourceIdAdapter = IntColumnAdapter,
                idAdapter = IntColumnAdapter,
                startYearAdapter = IntColumnAdapter,
                endYearAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                urlsAdapter = UrlsAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                storiesAdapter = StoriesResourceListAdapter(),
                charactersAdapter = CharactersResourceListAdapter(),
                creatorsAdapter = CreatorsResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter(),
                nextAdapter = SeriesSummaryAdapter(),
                previousAdapter = SeriesSummaryAdapter()
            ),
            storyEntityAdapter = StoryEntity.Adapter(
                idAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                seriesAdapter = SeriesResourceListAdapter(),
                charactersAdapter = CharactersResourceListAdapter(),
                creatorsAdapter = CreatorsResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter(),
                originalIssueAdapter = ComicSummaryAdapter()
            ),
            storyResourceEntityAdapter = StoryResourceEntity.Adapter(
                resourceTypeAdapter = EnumColumnAdapter<ResourceType>(),
                resourceIdAdapter = IntColumnAdapter,
                idAdapter = IntColumnAdapter,
                modifiedAdapter = LocalDateTimeAdapter(),
                thumbnailAdapter = ImageAdapter(),
                comicsAdapter = ComicsResourceListAdapter(),
                seriesAdapter = SeriesResourceListAdapter(),
                charactersAdapter = CharactersResourceListAdapter(),
                creatorsAdapter = CreatorsResourceListAdapter(),
                eventsAdapter = EventsResourceListAdapter(),
                originalIssueAdapter = ComicSummaryAdapter()
            )
        )
    }

    val requestDataSource: RequestDataSource by lazy {
        SqlDelightRequestDataSource(
            db = appDatabase
        )
    }
}