package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.entites.Story
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface StoryRepository {
    suspend fun requestStories(query: String? = null, sort: Sort = Sort.ASCENDING, limit: Int, offset: Int)
    suspend fun getStories(query: String? = null, sort: Sort = Sort.ASCENDING): CommonFlow<List<Story>>
}