package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Creator
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface CreatorRepository {
    suspend fun requestCreators(query: String? = null, sort: Sort = Sort.ASCENDING, limit: Int, offset: Int)
    suspend fun getCreators(query: String? = null, sort: Sort = Sort.ASCENDING): CommonFlow<List<Creator>>
}