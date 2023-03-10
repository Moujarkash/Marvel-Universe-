package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DataWrapperDto<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)
