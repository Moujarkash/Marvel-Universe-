package com.mod.marveluniverse.presentation.character.list

import com.mod.marveluniverse.domain.entites.Character
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.domain.error.AppException
import com.mod.marveluniverse.domain.repositories.CharacterRepository
import com.mod.marveluniverse.domain.utils.flows.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val characterRepository: CharacterRepository,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _characters = MutableStateFlow(emptyList<Character>())
    private val _state = MutableStateFlow(CharacterListState())
    val state = combine(
        _state,
        _characters
    ) { state, characters ->
        if (state.characters != characters) {
            _state.update {
                it.copy(
                    characters = characters,
                )
            }
            state.copy(characters = characters)
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharacterListState())
        .toCommonStateFlow()

    private val _limit = 20
    private var _requestDataJob: Job? = null

    init {
        requestCharacters(
            isRefresh = false,
            isInit = true,
            query = null,
            sort = _state.value.sort
        )
    }

    fun onEvent(event: CharacterListEvent) {
        when (event) {
            CharacterListEvent.RequestCharacters -> {
                requestCharacters(
                    isRefresh = false,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            CharacterListEvent.RefreshCharacters -> {
                requestCharacters(
                    isRefresh = true,
                    isInit = false,
                    query = _state.value.query,
                    sort = _state.value.sort
                )
            }
            is CharacterListEvent.SearchCharacters -> {
                _state.update {
                    it.copy(
                        query = event.query,
                    )
                }
                requestCharacters(
                    isRefresh = false,
                    isInit = true,
                    query = event.query,
                    sort = _state.value.sort
                )
            }
            CharacterListEvent.SearchTextCleared -> {
                _state.update {
                    it.copy(
                        query = null,
                    )
                }
                requestCharacters(
                    isRefresh = false,
                    isInit = true,
                    query = null,
                    sort = _state.value.sort
                )
            }
            is CharacterListEvent.SortCharacters -> {
                _state.update {
                    it.copy(
                        sort = event.sort,
                    )
                }
                requestCharacters(
                    isRefresh = false,
                    isInit = true,
                    query = _state.value.query,
                    sort = event.sort
                )
            }
            else -> Unit
        }
    }

    private fun requestCharacters(isRefresh: Boolean, isInit: Boolean, query: String?, sort: Sort) {
        if (_requestDataJob?.isActive == true) {
            _requestDataJob!!.cancel()
        }

        _state.update {
            it.copy(
                isFetchingCharacters = !isRefresh,
                isRefreshing = isRefresh,
                error = null
            )
        }

        _requestDataJob = viewModelScope.launch {
            try {
                characterRepository.requestCharacters(
                    query = query,
                    sort = sort,
                    limit = _limit,
                    offset = if (isRefresh || isInit) 0 else _state.value.characters.size
                )

                _state.update {
                    it.copy(
                        isFetchingCharacters = false,
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
                        isFetchingCharacters = false,
                        isRefreshing = false,
                        error = errorMessage
                    )
                }
            }

            characterRepository.getCharacters(_state.value.query, _state.value.sort).collect {
                _characters.value = it
            }
        }
    }
}