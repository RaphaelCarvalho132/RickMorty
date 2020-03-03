package com.raphael.carvalho.api.character.model

data class PaginationInfoVo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)
