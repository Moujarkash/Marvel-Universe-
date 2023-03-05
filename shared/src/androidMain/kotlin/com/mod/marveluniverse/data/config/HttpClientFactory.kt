package com.mod.marveluniverse.data.config

import com.mod.marveluniverse.data.ApiConstants
import com.mod.marveluniverse.domain.utils.EncryptionHelper
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

actual class HttpClientFactory {
    actual fun create(): HttpClient {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                   json = Json {
                       ignoreUnknownKeys = true
                       isLenient = true
                       coerceInputValues = true
                   }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }

        client.plugin(HttpSend).intercept { request ->
            val ts = Clock.System.now().toEpochMilliseconds()
            request.parameter("ts", ts)
            request.parameter("apikey", ApiConstants.API_PUBLIC_KEY)
            request.parameter(
                "hash",
                EncryptionHelper.md5("$ts${ApiConstants.API_PRIVATE_KEY}${ApiConstants.API_PUBLIC_KEY}")
            )

            execute(request)
        }

        return client
    }
}