package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Comic
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface ComicRepository {
    suspend fun requestComics(query: String?, limit: Int, offset: Int)
    fun getComics(): CommonFlow<List<Comic>>
    fun getComicById(id: Int): Comic
    suspend fun requestComicsResource(resourceType: ResourceType, resourceId: Int, limit: Int, offset: Int)
    fun getComicsResource(resourceType: ResourceType, resourceId: Int): CommonFlow<List<Comic>>
}