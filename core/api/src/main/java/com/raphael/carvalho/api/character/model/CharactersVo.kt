package com.raphael.carvalho.api.character.model

import com.google.gson.annotations.SerializedName

internal data class CharactersVo(
    @SerializedName("info")
    val info: PaginationInfoVo,
    @SerializedName("results")
    val characters: List<CharacterVo>
)
