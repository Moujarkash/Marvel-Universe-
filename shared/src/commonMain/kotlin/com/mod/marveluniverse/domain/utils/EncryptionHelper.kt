package com.mod.marveluniverse.domain.utils

expect object EncryptionHelper {
    fun md5(input: String): String
}