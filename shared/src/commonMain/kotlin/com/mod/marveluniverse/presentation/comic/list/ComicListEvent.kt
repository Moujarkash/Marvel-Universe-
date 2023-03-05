package com.mod.marveluniverse.presentation.comic.list

sealed class ComicListEvent {
    data class RequestComics(val query: String? = null, val isRefresh: Boolean = false): ComicListEvent()
}
