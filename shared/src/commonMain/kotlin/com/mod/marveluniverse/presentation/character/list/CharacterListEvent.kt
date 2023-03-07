package com.mod.marveluniverse.presentation.character.list

import com.mod.marveluniverse.domain.entites.ResourceType

sealed class CharacterListEvent {
    data class DrawerItemClicked(val resourceType: ResourceType): CharacterListEvent()
}
