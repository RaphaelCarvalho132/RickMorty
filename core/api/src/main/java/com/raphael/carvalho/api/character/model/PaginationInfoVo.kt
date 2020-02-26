package com.raphael.carvalho.api.character.model

internal data class PaginationInfoVo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)
