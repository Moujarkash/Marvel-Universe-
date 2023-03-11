package com.mod.marveluniverse.domain.repositories

import com.mod.marveluniverse.domain.entites.Character
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface CharacterRepository {
    suspend fun requestCharacters(query: String? = null, sort: Sort = Sort.ASCENDING, limit: Int, offset: Int)
    suspend fun getCharacters(query: String? = null, sort: Sort = Sort.ASCENDING): CommonFlow<List<Character>>
}