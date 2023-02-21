package com.mod.marveluniverse.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWrapperDto<T>(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val etag: String,
    val data: DataWrapperDto<T>
)
