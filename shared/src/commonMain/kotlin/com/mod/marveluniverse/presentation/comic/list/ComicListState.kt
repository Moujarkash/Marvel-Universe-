package com.mod.marveluniverse.presentation.comic.list

import com.mod.marveluniverse.domain.entites.Comic

data class ComicListState(
    val comics: List<Comic> = emptyList(),
    val isFetchingComics: Boolean = false,
    val error: String? = null
)