package com.mod.marveluniverse.data.utils

object ResourceHelper {
    fun getResourceIdFromResourceUrl(resourceUrl: String): Int =
        resourceUrl.split("/").last().toIntOrNull() ?: -1
}