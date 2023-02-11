package com.mod.marveluniverse.domain.repositories

import com.kuuurt.paging.multiplatform.PagingData
import com.mod.marveluniverse.domain.entites.Series
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface SeriesRepository {
    fun getSeries(query: String?, limit: Int, offset: Int): CommonFlow<PagingData<Series>>
    fun getSeries(id: Int): Series
    fun getSeriesByCharacterId(characterId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Series>>
    fun getSeriesByComicId(comicId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Series>>
    fun getSeriesByCreatorId(creatorId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Series>>
    fun getSeriesByEventId(eventId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Series>>
    fun getSeriesByStoryId(storyId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Series>>
}