package com.mod.marveluniverse.presentation.story.list

import com.mod.marveluniverse.domain.entites.Story
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.StoryRepository
import com.mod.marveluniverse.domain.utils.flows.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StoryListViewModel(
    private val storyRepository: StoryRepository,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _stories = MutableStateFlow(emptyList<Story>())
    private val _state = MutableStateFlow(StoryListState())
    val state = combine(
        _state,
        _stories
    ) { state, stories ->
        if (state.stories != stories) {
            _state.update {
                it.copy(
                    stories = stories,
                )
            }
            state.copy(stories = stories)
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StoryListState())
        .toCommonStateFlow()

    private val _limit = 20
    private var _requestDataJob: Job? = null

    init {
        requestStories(
            isRefresh = false,
            isInit = true,
            query = null,
            sort = _state.value.sort
        )
    }

    fun onEvent(event: StoryListEvent) {
        when (event) {
            StoryListEvent.RequestStories -> {
                requestStories(
                    isRefresh = false,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            StoryListEvent.RefreshStories -> {
                requestStories(
                    isRefresh = true,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            is StoryListEvent.SearchStories -> {
                _state.update {
                    it.copy(
                        query = event.query,
                    )
                }
                requestStories(
                    isRefresh = false,
                    isInit = true,
                    query = event.query,
                    sort = _state.value.sort
                )
            }
            StoryListEvent.SearchTextCleared -> {
                _state.update {
                    it.copy(
                        query = null,
                    )
                }
                requestStories(
                    isRefresh = false,
                    isInit = true,
                    query = null,
                    sort = _state.value.sort
                )
            }
            is StoryListEvent.SortStories -> {
                _state.update {
                    it.copy(
                        sort = event.sort,
                    )
                }
                requestStories(
                    isRefresh = false,
                    isInit = true,
                    query = _state.value.query,
                    sort = event.sort
                )
            }
            else -> Unit
        }
    }

    private fun requestStories(isRefresh: Boolean, isInit: Boolean, query: String?, sort: Sort) {
        if (_requestDataJob?.isActive == true) {
            _requestDataJob!!.cancel()
        }

        _state.update {
            it.copy(
                isFetchingStories = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            )
        }

        _requestDataJob = viewModelScope.launch {
            try {
                storyRepository.requestStories(
                    query = query,
                    sort = sort,
                    limit = _limit,
                    offset = if (isRefresh || isInit) 0 else _state.value.stories.size
                )

                _state.update {
                    it.copy(
                        isFetchingStories = false,
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
                        isFetchingStories = false,
                        isRefreshing = false,
                        error = errorMessage
                    )
                }
            }

            storyRepository.getStories(_state.value.query, _state.value.sort).collect {
                _stories.value = it
            }
        }
    }
}