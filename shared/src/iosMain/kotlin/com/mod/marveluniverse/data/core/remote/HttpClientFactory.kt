package com.mod.marveluniverse.data.core.remote

import com.mod.marveluniverse.data.core.ApiConstants
import com.soywiz.krypto.md5
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.core.*
import kotlinx.datetime.Clock

actual class HttpClientFactory {
    actual fun create(): HttpClient {
        val client = HttpClient(Darwin) {
            install(ContentNegotiation) {
                json()
            }
        }

        client.plugin(HttpSend).intercept { request ->
            val ts = Clock.System.now().toEpochMilliseconds()
            request.parameter("ts", ts)
            request.parameter("apikey", ApiConstants.API_PUBLIC_KEY)
            request.parameter("hash",
                "$ts${ApiConstants.API_PRIVATE_KEY}${ApiConstants.API_PUBLIC_KEY}".toByteArray()
                    .md5().base64
            )

            execute(request)
        }

        return client
    }
}