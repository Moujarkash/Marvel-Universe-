package com.mod.marveluniverse.domain.repositories

import com.kuuurt.paging.multiplatform.PagingData
import com.mod.marveluniverse.domain.entites.Comic
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface ComicRepository {
    fun getComics(query: String?, limit: Int, offset: Int): CommonFlow<PagingData<Comic>>
    fun getComic(id: Int): Comic
    fun getComicsByCharacterId(characterId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Comic>>
    fun getComicsByCreatorId(creatorId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Comic>>
    fun getComicsByEventId(eventId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Comic>>
    fun getComicsBySeriesId(seriesId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Comic>>
    fun getComicsByStoryId(storyId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Comic>>
}