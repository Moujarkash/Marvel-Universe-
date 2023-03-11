package com.mod.marveluniverse.android.series.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mod.marveluniverse.domain.repositories.SeriesRepository
import com.mod.marveluniverse.presentation.series.list.SeriesListEvent
import com.mod.marveluniverse.presentation.series.list.SeriesListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidSeriesListViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
): ViewModel() {
    private val viewModel by lazy {
        SeriesListViewModel(
            seriesRepository = seriesRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: SeriesListEvent) {
        viewModel.onEvent(event)
    }
}