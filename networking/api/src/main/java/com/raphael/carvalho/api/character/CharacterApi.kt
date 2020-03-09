package com.raphael.carvalho.api.character

import com.raphael.carvalho.api.character.model.CharacterVo
import com.raphael.carvalho.api.character.model.CharactersVo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    @GET("/character/")
    suspend fun listCharacters(@Query("page") page: Int): CharactersVo

    @GET("/character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterVo
}
