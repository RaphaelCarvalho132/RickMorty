package com.raphael.carvalho.api.character

import com.raphael.carvalho.api.character.model.Character
import com.raphael.carvalho.api.character.model.Characters
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    @GET("/character/")
    suspend fun listCharacters(@Query("page") page: Long): Characters

    @GET("/character/{id}")
    suspend fun getCharacter(@Path("id") id: Long): Character
}
