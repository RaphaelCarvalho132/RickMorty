package com.raphael.carvalho.api.character.model

data class PaginationInfo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)
