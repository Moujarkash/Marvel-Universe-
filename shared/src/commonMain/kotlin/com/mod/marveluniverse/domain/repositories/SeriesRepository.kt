package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Series
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface SeriesRepository {
    suspend fun requestSeries(query: String? = null, sort: Sort = Sort.ASCENDING, limit: Int, offset: Int)
    suspend fun getSeries(query: String? = null, sort: Sort = Sort.ASCENDING): CommonFlow<List<Series>>
}