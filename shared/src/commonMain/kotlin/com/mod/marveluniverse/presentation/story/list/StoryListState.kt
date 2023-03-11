package com.mod.marveluniverse.presentation.story.list

import com.mod.marveluniverse.domain.entites.Story
import com.mod.marveluniverse.domain.entites.Sort

data class StoryListState(
    val stories: List<Story> = emptyList(),
    val isFetchingStories: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
    val query: String? = null,
    val sort: Sort = Sort.ASCENDING
)
