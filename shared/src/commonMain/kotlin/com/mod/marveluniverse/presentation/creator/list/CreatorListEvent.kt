package com.mod.marveluniverse.presentation.creator.list

import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort

sealed class CreatorListEvent {
    object RequestCreators: CreatorListEvent()
    object RefreshCreators: CreatorListEvent()
    data class DrawerItemClicked(val resourceType: ResourceType): CreatorListEvent()
    data class SearchCreators(val query: String): CreatorListEvent()
    object SearchTextCleared: CreatorListEvent()
    data class SortCreators(val sort: Sort): CreatorListEvent()
}
