package com.mod.marveluniverse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform