package com.mod.marveluniverse.domain.repositories

import com.kuuurt.paging.multiplatform.PagingData
import com.mod.marveluniverse.domain.entites.Character
import com.mod.marveluniverse.domain.utils.flows.CommonFlow

interface CharacterRepository {
    fun getCharacters(query: String?, limit: Int, offset: Int): CommonFlow<PagingData<Character>>
    fun getCharacter(id: Int): Character
    fun getCharactersByComicId(comicId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Character>>
    fun getCharactersByEventId(eventId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Character>>
    fun getCharactersBySeriesId(seriesId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Character>>
    fun getCharactersByStoryId(storyId: Int, limit: Int, offset: Int): CommonFlow<PagingData<Character>>
}