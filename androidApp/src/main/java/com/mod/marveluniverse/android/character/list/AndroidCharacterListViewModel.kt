package com.mod.marveluniverse.android.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mod.marveluniverse.domain.repositories.CharacterRepository
import com.mod.marveluniverse.presentation.character.list.CharacterListEvent
import com.mod.marveluniverse.presentation.character.list.CharacterListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidCharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
): ViewModel() {
    private val viewModel by lazy {
        CharacterListViewModel(
            characterRepository = characterRepository,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterListEvent) {
        viewModel.onEvent(event)
    }
}