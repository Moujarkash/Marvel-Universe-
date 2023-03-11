package com.mod.marveluniverse.presentation.character.list

import com.mod.marveluniverse.domain.entites.Character
import com.mod.marveluniverse.domain.entites.Sort

data class CharacterListState(
    val characters: List<Character> = emptyList(),
    val isFetchingCharacters: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val query: String? = null,
    val sort: Sort = Sort.ASCENDING
)
