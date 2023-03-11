package com.mod.marveluniverse.presentation.series.list

import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort

sealed class SeriesListEvent {
    object RequestSeries: SeriesListEvent()
    object RefreshSeries: SeriesListEvent()
    data class DrawerItemClicked(val resourceType: ResourceType): SeriesListEvent()
    data class SearchSeries(val query: String): SeriesListEvent()
    object SearchTextCleared: SeriesListEvent()
    data class SortSeries(val sort: Sort): SeriesListEvent()
}
