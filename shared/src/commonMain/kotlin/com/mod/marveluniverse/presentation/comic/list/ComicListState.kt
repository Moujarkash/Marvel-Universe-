package com.mod.marveluniverse.presentation.comic.list

import com.mod.marveluniverse.domain.entites.Comic
import com.mod.marveluniverse.domain.entites.Sort

data class ComicListState(
    val comics: List<Comic> = emptyList(),
    val isFetchingComics: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val query: String? = null,
    val sort: Sort = Sort.ASCENDING
)