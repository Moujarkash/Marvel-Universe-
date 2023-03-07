package com.mod.marveluniverse.presentation.comic.list

import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.ComicRepository
import com.mod.marveluniverse.domain.utils.flows.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ComicListViewModel(
    private val comicRepository: ComicRepository,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ComicListState())
    val state = combine(
        _state,
        comicRepository.getComics()
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

    init {
        requestComics(
            isRefresh = false,
            isInit = true,
            query = null
        )
    }

    fun onEvent(event: ComicListEvent) {
        when (event) {
            is ComicListEvent.RequestComics -> {
                requestComics(
                    isRefresh = _state.value.isRefreshing,
                    isInit = false,
                    query = _state.value.query
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
                    query = event.query
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
                    query = null
                )
            }
            else -> Unit
        }
    }

    private fun requestComics(isRefresh: Boolean, isInit: Boolean, query: String?) {
        _state.update {
            it.copy(
                isFetchingComics = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                comicRepository.requestComics(
                    query = query,
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
        }
    }
}