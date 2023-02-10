@file:OptIn(ExperimentalUnsignedTypes::class)

package com.mod.marveluniverse.domain.utils

import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlinx.cinterop.*
import platform.Security.*
import platform.Foundation.*
import platform.CoreCrypto.*

actual object EncryptionHelper {
    actual fun md5(input: String): String {
        val encodedInput = String(input.toByteArray(), 0, input.length, Charsets.UTF_8)

        val digest = UByteArray(CC_MD5_DIGEST_LENGTH)
        encodedInput.usePinned { inputPinned ->
            digest.usePinned { digestPinned ->
                CC_MD5(inputPinned.addressOf(0), encodedInput.length.convert(), digestPinned.addressOf(0))
            }
        }

        return digest.toHexString()
    }
}

fun UByteArray.toHexString(): String = buildString(size * 2) {
    for (ubyte in this@toHexString) {
        append((ubyte.toInt() shr 4 and 15).digitToChar(radix = 16))
        append((ubyte.toInt() and 15).digitToChar(radix = 16))
    }
}