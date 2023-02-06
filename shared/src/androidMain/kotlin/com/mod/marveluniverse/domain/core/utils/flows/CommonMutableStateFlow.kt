package com.mod.marveluniverse.domain.core.utils.flows

import kotlinx.coroutines.flow.MutableStateFlow

actual class CommonMutableStateFlow<T> actual constructor(
    private val mutableStateFlow: MutableStateFlow<T>
): MutableStateFlow<T> by mutableStateFlow