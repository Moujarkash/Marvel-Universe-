package com.mod.marveluniverse.presentation.comic.list

import com.mod.marveluniverse.domain.entites.Comic
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.ComicRepository
import com.mod.marveluniverse.domain.utils.flows.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ComicListViewModel(
    private val comicRepository: ComicRepository,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _comics = MutableStateFlow(emptyList<Comic>())
    private val _state = MutableStateFlow(ComicListState())
    val state = combine(
        _state,
        _comics
    ) { state, comics ->
        if (state.comics != comics) {
            _state.update {
                it.copy(
                    comics = comics,
                )
            }
            state.copy(comics = comics)
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ComicListState())
        .toCommonStateFlow()

    private val _limit = 20
    private var _requestDataJob: Job? = null

    init {
        requestComics(
            isRefresh = false,
            isInit = true,
            query = null,
            sort = _state.value.sort
        )
    }

    fun onEvent(event: ComicListEvent) {
        when (event) {
            ComicListEvent.RequestComics -> {
                requestComics(
                    isRefresh = false,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            ComicListEvent.RefreshComics -> {
                requestComics(
                    isRefresh = true,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            is ComicListEvent.SearchComics -> {
                _state.update {
                    it.copy(
                        query = event.query,
                    )
                }
                requestComics(
                    isRefresh = false,
                    isInit = true,
                    query = event.query,
                    sort = _state.value.sort
                )
            }
            ComicListEvent.SearchTextCleared -> {
                _state.update {
                    it.copy(
                        query = null,
                    )
                }
                requestComics(
                    isRefresh = false,
                    isInit = true,
                    query = null,
                    sort = _state.value.sort
                )
            }
            is ComicListEvent.SortComics -> {
                _state.update {
                    it.copy(
                        sort = event.sort,
                    )
                }
                requestComics(
                    isRefresh = false,
                    isInit = true,
                    query = _state.value.query,
                    sort = event.sort
                )
            }
            else -> Unit
        }
    }

    private fun requestComics(isRefresh: Boolean, isInit: Boolean, query: String?, sort: Sort) {
        if (_requestDataJob?.isActive == true) {
            _requestDataJob!!.cancel()
        }

        _state.update {
            it.copy(
                isFetchingComics = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            )
        }

        _requestDataJob = viewModelScope.launch {
            try {
                comicRepository.requestComics(
                    query = query,
                    sort = sort,
                    limit = _limit,
                    offset = if (isRefresh || isInit) 0 else _state.value.comics.size
                )

                _state.update {
                    it.copy(
                        isFetchingComics = false,
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
                        isFetchingComics = false,
                        isRefreshing = false,
                        error = errorMessage
                    )
                }
            }

            comicRepository.getComics(_state.value.query, _state.value.sort).collect {
                _comics.value = it
            }
        }
    }
}