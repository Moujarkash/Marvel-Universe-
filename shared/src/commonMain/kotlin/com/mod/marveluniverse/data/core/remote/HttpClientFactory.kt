package com.mod.marveluniverse.data.core.remote

import io.ktor.client.*

expect class HttpClientFactory {
    fun create(): HttpClient
}