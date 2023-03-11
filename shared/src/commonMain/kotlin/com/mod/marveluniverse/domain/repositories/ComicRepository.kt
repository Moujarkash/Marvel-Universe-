package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Comic
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface ComicRepository {
    suspend fun requestComics(query: String? = null, sort: Sort = Sort.ASCENDING, limit: Int, offset: Int)
    suspend fun getComics(query: String? = null, sort: Sort = Sort.ASCENDING): CommonFlow<List<Comic>>
}