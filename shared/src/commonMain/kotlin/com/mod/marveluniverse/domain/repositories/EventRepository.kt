package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Event
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface EventRepository {
    suspend fun requestEvents(query: String? = null, sort: Sort = Sort.ASCENDING, limit: Int, offset: Int)
    suspend fun getEvents(query: String? = null, sort: Sort = Sort.ASCENDING): CommonFlow<List<Event>>
}