package com.mod.marveluniverse.android.event.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mod.marveluniverse.domain.repositories.EventRepository
import com.mod.marveluniverse.presentation.event.list.EventListEvent
import com.mod.marveluniverse.presentation.event.list.EventListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidEventListViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {
    private val viewModel by lazy {
        EventListViewModel(
            eventRepository = eventRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: EventListEvent) {
        viewModel.onEvent(event)
    }
}