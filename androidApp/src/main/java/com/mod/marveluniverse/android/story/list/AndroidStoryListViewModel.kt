package com.mod.marveluniverse.android.story.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mod.marveluniverse.domain.repositories.StoryRepository
import com.mod.marveluniverse.presentation.story.list.StoryListEvent
import com.mod.marveluniverse.presentation.story.list.StoryListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidStoryListViewModel @Inject constructor(
    private val storyRepository: StoryRepository
): ViewModel() {
    private val viewModel by lazy {
        StoryListViewModel(
            storyRepository = storyRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: StoryListEvent) {
        viewModel.onEvent(event)
    }
}