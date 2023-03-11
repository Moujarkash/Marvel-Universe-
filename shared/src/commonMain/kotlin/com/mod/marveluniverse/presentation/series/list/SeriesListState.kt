package com.mod.marveluniverse.presentation.series.list

import com.mod.marveluniverse.domain.entites.Series
import com.mod.marveluniverse.domain.entites.Sort

data class SeriesListState(
    val series: List<Series> = emptyList(),
    val isFetchingSeries: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val query: String? = null,
    val sort: Sort = Sort.ASCENDING
)