package com.mod.marveluniverse.domain.utils.flows

import kotlinx.coroutines.flow.StateFlow

expect class CommonStateFlow<T>(stateFlow: StateFlow<T>): StateFlow<T>

fun <T> StateFlow<T>.toCommonStateFlow() = CommonStateFlow(this)