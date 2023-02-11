package com.mod.marveluniverse.domain.repositories

import com.kuuurt.paging.multiplatform.PagingData
import com.mod.marveluniverse.domain.entites.Event
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface EventRepository {
    fun getEvents(query: String?, limit: Int, offset: Int): CommonFlow<PagingData<Event>>
    fun getEvent(id: Int): Event
    fun getEventsByCharacterId(characterId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Event>>
    fun getEventsByComicId(comicId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Event>>
    fun getEventsByCreatorId(creatorId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Event>>
    fun getEventsBySeriesId(seriesId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Event>>
    fun getEventsByStoryId(storyId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Event>>
}