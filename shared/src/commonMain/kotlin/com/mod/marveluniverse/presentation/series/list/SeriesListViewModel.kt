package com.mod.marveluniverse.presentation.series.list

import com.mod.marveluniverse.domain.entites.Series
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.SeriesRepository
import com.mod.marveluniverse.domain.utils.flows.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SeriesListViewModel(
    private val seriesRepository: SeriesRepository,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _series = MutableStateFlow(emptyList<Series>())
    private val _state = MutableStateFlow(SeriesListState())
    val state = combine(
        _state,
        _series
    ) { state, series ->
        if (state.series != series) {
            _state.update {
                it.copy(
                    series = series,
                )
            }
            state.copy(series = series)
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SeriesListState())
        .toCommonStateFlow()

    private val _limit = 20
    private var _requestDataJob: Job? = null

    init {
        requestSeries(
            isRefresh = false,
            isInit = true,
            query = null,
            sort = _state.value.sort
        )
    }

    fun onEvent(event: SeriesListEvent) {
        when (event) {
            SeriesListEvent.RequestSeries -> {
                requestSeries(
                    isRefresh = false,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            SeriesListEvent.RefreshSeries -> {
                requestSeries(
                    isRefresh = true,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            is SeriesListEvent.SearchSeries -> {
                _state.update {
                    it.copy(
                        query = event.query,
                    )
                }
                requestSeries(
                    isRefresh = false,
                    isInit = true,
                    query = event.query,
                    sort = _state.value.sort
                )
            }
            SeriesListEvent.SearchTextCleared -> {
                _state.update {
                    it.copy(
                        query = null,
                    )
                }
                requestSeries(
                    isRefresh = false,
                    isInit = true,
                    query = null,
                    sort = _state.value.sort
                )
            }
            is SeriesListEvent.SortSeries -> {
                _state.update {
                    it.copy(
                        sort = event.sort,
                    )
                }
                requestSeries(
                    isRefresh = false,
                    isInit = true,
                    query = _state.value.query,
                    sort = event.sort
                )
            }
            else -> Unit
        }
    }

    private fun requestSeries(isRefresh: Boolean, isInit: Boolean, query: String?, sort: Sort) {
        if (_requestDataJob?.isActive == true) {
            _requestDataJob!!.cancel()
        }

        _state.update {
            it.copy(
                isFetchingSeries = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            )
        }

        _requestDataJob = viewModelScope.launch {
            try {
                seriesRepository.requestSeries(
                    query = query,
                    sort = sort,
                    limit = _limit,
                    offset = if (isRefresh || isInit) 0 else _state.value.series.size
                )

                _state.update {
                    it.copy(
                        isFetchingSeries = false,
                        isRefreshing = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                val defaultErrorMessage = "Something went wrong!"
                val errorMessage: String = when (e) {
                    is AppException -> e.errorMessage ?: defaultErrorMessage
                    else -> defaultErrorMessage
                }
                _state.update {
                    it.copy(
                        isFetchingSeries = false,
                        isRefreshing = false,
                        error = errorMessage
                    )
                }
            }

            seriesRepository.getSeries(_state.value.query, _state.value.sort).collect {
                _series.value = it
            }
        }
    }
}