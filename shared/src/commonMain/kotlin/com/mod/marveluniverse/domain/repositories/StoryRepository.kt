package com.mod.marveluniverse.domain.repositories

import com.kuuurt.paging.multiplatform.PagingData
import com.mod.marveluniverse.domain.entites.Story
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface StoryRepository {
    fun getStories(query: String?, limit: Int, offset: Int): CommonFlow<PagingData<Story>>
    fun getStory(id: Int): Story
    fun getStoriesByCharacterId(characterId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Story>>
    fun getStoriesByComicId(comicId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Story>>
    fun getStoriesByCreatorId(creatorId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Story>>
    fun getStoriesBySeriesId(seriesId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Story>>
    fun getStoriesByEventId(eventId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Story>>
}