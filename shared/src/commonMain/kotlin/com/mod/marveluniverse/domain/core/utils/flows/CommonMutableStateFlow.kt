package com.mod.marveluniverse.domain.core.utils.flows

import kotlinx.coroutines.flow.MutableStateFlow

expect class CommonMutableStateFlow<T>(mutableStateFlow: MutableStateFlow<T>): MutableStateFlow<T>

fun <T> MutableStateFlow<T>.toCommonMutableStateFlow() = CommonMutableStateFlow(this)