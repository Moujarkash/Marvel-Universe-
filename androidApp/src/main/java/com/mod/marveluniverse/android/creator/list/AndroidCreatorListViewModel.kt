package com.mod.marveluniverse.android.creator.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mod.marveluniverse.domain.repositories.CreatorRepository
import com.mod.marveluniverse.presentation.creator.list.CreatorListEvent
import com.mod.marveluniverse.presentation.creator.list.CreatorListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidCreatorListViewModel @Inject constructor(
    private val creatorRepository: CreatorRepository
): ViewModel() {
    private val viewModel by lazy {
        CreatorListViewModel(
            creatorRepository = creatorRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: CreatorListEvent) {
        viewModel.onEvent(event)
    }
}