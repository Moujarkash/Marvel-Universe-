package com.mod.marveluniverse.domain.repositories

import com.kuuurt.paging.multiplatform.PagingData
import com.mod.marveluniverse.domain.entites.Creator
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface CreatorRepository {
    fun getCreators(query: String?, limit: Int, offset: Int): CommonFlow<PagingData<Creator>>
    fun getCreator(id: Int): Creator
    fun getCreatorsByComicId(comicId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Creator>>
    fun getCreatorsByEventId(eventId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Creator>>
    fun getCreatorsBySeriesId(seriesId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Creator>>
    fun getCreatorsByStoryId(storyId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Creator>>
}