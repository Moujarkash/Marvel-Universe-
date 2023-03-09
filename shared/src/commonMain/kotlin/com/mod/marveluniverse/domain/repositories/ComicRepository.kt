package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Comic
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface ComicRepository {
    suspend fun requestComics(query: String? = null, sort: Sort = Sort.ASCENDING, limit: Int, offset: Int)
    suspend fun getComics(query: String? = null, sort: Sort = Sort.ASCENDING): CommonFlow<List<Comic>>
    suspend fun getComicById(id: Int): Comic
    suspend fun requestComicsForEntity(relatedEntity: ResourceType, relatedEntityId: Int, limit: Int, offset: Int)
    suspend fun getComicsEntity(relatedEntity: ResourceType, relatedEntityId: Int): CommonFlow<List<Comic>>
}