package com.mod.marveluniverse.presentation.creator.list

import com.mod.marveluniverse.domain.entites.Creator
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.CreatorRepository
import com.mod.marveluniverse.domain.utils.flows.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CreatorListViewModel(
    private val creatorRepository: CreatorRepository,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _creators = MutableStateFlow(emptyList<Creator>())
    private val _state = MutableStateFlow(CreatorListState())
    val state = combine(
        _state,
        _creators
    ) { state, creators ->
        if (state.creators != creators) {
            _state.update {
                it.copy(
                    creators = creators,
                )
            }
            state.copy(creators = creators)
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreatorListState())
        .toCommonStateFlow()

    private val _limit = 20
    private var _requestDataJob: Job? = null

    init {
        requestCreators(
            isRefresh = false,
            isInit = true,
            query = null,
            sort = _state.value.sort
        )
    }

    fun onEvent(event: CreatorListEvent) {
        when (event) {
            CreatorListEvent.RequestCreators -> {
                requestCreators(
                    isRefresh = false,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            CreatorListEvent.RefreshCreators -> {
                requestCreators(
                    isRefresh = true,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            is CreatorListEvent.SearchCreators -> {
                _state.update {
                    it.copy(
                        query = event.query,
                    )
                }
                requestCreators(
                    isRefresh = false,
                    isInit = true,
                    query = event.query,
                    sort = _state.value.sort
                )
            }
            CreatorListEvent.SearchTextCleared -> {
                _state.update {
                    it.copy(
                        query = null,
                    )
                }
                requestCreators(
                    isRefresh = false,
                    isInit = true,
                    query = null,
                    sort = _state.value.sort
                )
            }
            is CreatorListEvent.SortCreators -> {
                _state.update {
                    it.copy(
                        sort = event.sort,
                    )
                }
                requestCreators(
                    isRefresh = false,
                    isInit = true,
                    query = _state.value.query,
                    sort = event.sort
                )
            }
            else -> Unit
        }
    }

    private fun requestCreators(isRefresh: Boolean, isInit: Boolean, query: String?, sort: Sort) {
        if (_requestDataJob?.isActive == true) {
            _requestDataJob!!.cancel()
        }

        _state.update {
            it.copy(
                isFetchingCreators = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            )
        }

        _requestDataJob = viewModelScope.launch {
            try {
                creatorRepository.requestCreators(
                    query = query,
                    sort = sort,
                    limit = _limit,
                    offset = if (isRefresh || isInit) 0 else _state.value.creators.size
                )

                _state.update {
                    it.copy(
                        isFetchingCreators = false,
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
                        isFetchingCreators = false,
                        isRefreshing = false,
                        error = errorMessage
                    )
                }
            }

            creatorRepository.getCreators(_state.value.query, _state.value.sort).collect {
                _creators.value = it
            }
        }
    }
}