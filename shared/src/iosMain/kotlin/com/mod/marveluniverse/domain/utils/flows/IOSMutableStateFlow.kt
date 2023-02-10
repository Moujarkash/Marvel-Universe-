package com.mod.marveluniverse.domain.utils.flows

import com.mod.marveluniverse.domain.utils.flows.CommonMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(
    initialValue: T
) : CommonMutableStateFlow<T>(MutableStateFlow(initialValue))