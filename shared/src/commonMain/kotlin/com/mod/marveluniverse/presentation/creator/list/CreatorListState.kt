package com.mod.marveluniverse.presentation.creator.list

import com.mod.marveluniverse.domain.entites.Creator
import com.mod.marveluniverse.domain.entites.Sort

data class CreatorListState(
    val creators: List<Creator> = emptyList(),
    val isFetchingCreators: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val query: String? = null,
    val sort: Sort = Sort.ASCENDING
)