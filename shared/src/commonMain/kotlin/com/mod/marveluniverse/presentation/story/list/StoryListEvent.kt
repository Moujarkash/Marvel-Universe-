package com.mod.marveluniverse.presentation.story.list

import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.domain.entites.Sort

sealed class StoryListEvent {
    object RequestStories: StoryListEvent()
    object RefreshStories: StoryListEvent()
    data class DrawerItemClicked(val resourceType: ResourceType): StoryListEvent()
    data class SearchStories(val query: String): StoryListEvent()
    object SearchTextCleared: StoryListEvent()
    data class SortStories(val sort: Sort): StoryListEvent()
}
