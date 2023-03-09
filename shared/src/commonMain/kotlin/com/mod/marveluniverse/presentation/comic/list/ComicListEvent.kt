package com.mod.marveluniverse.presentation.comic.list

import com.mod.marveluniverse.domain.entites.ResourceType

sealed class ComicListEvent {
    object RequestComics: ComicListEvent()
    object RefreshComics: ComicListEvent()
    data class DrawerItemClicked(val resourceType: ResourceType): ComicListEvent()
    data class SearchComics(val query: String): ComicListEvent()
    object SearchTextCleared: ComicListEvent()
}
