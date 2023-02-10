package com.mod.marveluniverse.data.config

import io.ktor.client.*

expect class HttpClientFactory {
    fun create(): HttpClient
}