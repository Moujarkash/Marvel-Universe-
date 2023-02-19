package com.mod.marveluniverse.domain.entites

data class CharactersResourceList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<CharacterSummary>
)
