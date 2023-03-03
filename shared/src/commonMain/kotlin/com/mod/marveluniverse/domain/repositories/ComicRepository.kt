package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Comic
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface ComicRepository {
    suspend fun requestComics(query: String? = null, limit: Int, offset: Int)
    fun getComics(): CommonFlow<List<Comic>>
    fun getComicById(id: Int): Comic
    suspend fun requestComicsForEntity(relatedEntity: ResourceType, relatedEntityId: Int, limit: Int, offset: Int)
    fun getComicsEntity(relatedEntity: ResourceType, relatedEntityId: Int): CommonFlow<List<Comic>>
}