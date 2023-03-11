package com.mod.marveluniverse.presentation.character.list

import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort

sealed class CharacterListEvent {
    object RequestCharacters: CharacterListEvent()
    object RefreshCharacters: CharacterListEvent()
    data class DrawerItemClicked(val resourceType: ResourceType): CharacterListEvent()
    data class SearchCharacters(val query: String): CharacterListEvent()
    object SearchTextCleared: CharacterListEvent()
    data class SortCharacters(val sort: Sort): CharacterListEvent()
}
