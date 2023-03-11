package com.mod.marveluniverse.presentation.event.list

import com.mod.marveluniverse.domain.entites.Event
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.EventRepository
import com.mod.marveluniverse.domain.utils.flows.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EventListViewModel(
    private val eventRepository: EventRepository,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _events = MutableStateFlow(emptyList<Event>())
    private val _state = MutableStateFlow(EventListState())
    val state = combine(
        _state,
        _events
    ) { state, events ->
        if (state.events != events) {
            _state.update {
                it.copy(
                    events = events,
                )
            }
            state.copy(events = events)
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EventListState())
        .toCommonStateFlow()

    private val _limit = 20
    private var _requestDataJob: Job? = null

    init {
        requestEvents(
            isRefresh = false,
            isInit = true,
            query = null,
            sort = _state.value.sort
        )
    }

    fun onEvent(event: EventListEvent) {
        when (event) {
            EventListEvent.RequestEvents -> {
                requestEvents(
                    isRefresh = false,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            EventListEvent.RefreshEvents -> {
                requestEvents(
                    isRefresh = true,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            is EventListEvent.SearchEvents -> {
                _state.update {
                    it.copy(
                        query = event.query,
                    )
                }
                requestEvents(
                    isRefresh = false,
                    isInit = true,
                    query = event.query,
                    sort = _state.value.sort
                )
            }
            EventListEvent.SearchTextCleared -> {
                _state.update {
                    it.copy(
                        query = null,
                    )
                }
                requestEvents(
                    isRefresh = false,
                    isInit = true,
                    query = null,
                    sort = _state.value.sort
                )
            }
            is EventListEvent.SortEvents -> {
                _state.update {
                    it.copy(
                        sort = event.sort,
                    )
                }
                requestEvents(
                    isRefresh = false,
                    isInit = true,
                    query = _state.value.query,
                    sort = event.sort
                )
            }
            else -> Unit
        }
    }

    private fun requestEvents(isRefresh: Boolean, isInit: Boolean, query: String?, sort: Sort) {
        if (_requestDataJob?.isActive == true) {
            _requestDataJob!!.cancel()
        }

        _state.update {
            it.copy(
                isFetchingEvents = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            )
        }

        _requestDataJob = viewModelScope.launch {
            try {
                eventRepository.requestEvents(
                    query = query,
                    sort = sort,
                    limit = _limit,
                    offset = if (isRefresh || isInit) 0 else _state.value.events.size
                )

                _state.update {
                    it.copy(
                        isFetchingEvents = false,
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
                        isFetchingEvents = false,
                        isRefreshing = false,
                        error = errorMessage
                    )
                }
            }

            eventRepository.getEvents(_state.value.query, _state.value.sort).collect {
                _events.value = it
            }
        }
    }
}