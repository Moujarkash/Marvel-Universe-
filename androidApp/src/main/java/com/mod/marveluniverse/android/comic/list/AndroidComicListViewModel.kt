package com.mod.marveluniverse.android.comic.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mod.marveluniverse.domain.repositories.ComicRepository
import com.mod.marveluniverse.presentation.comic.list.ComicListEvent
import com.mod.marveluniverse.presentation.comic.list.ComicListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidComicListViewModel @Inject constructor(
    private val comicRepository: ComicRepository
): ViewModel() {
    private val viewModel by lazy {
        ComicListViewModel(
            comicRepository = comicRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: ComicListEvent) {
        viewModel.onEvent(event)
    }
}