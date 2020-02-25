package com.raphael.carvalho.api.character.model

data class Characters(
    val info: PaginationInfo,
    val results: List<Character>
)