package com.raphael.carvalho.api.character

import com.raphael.carvalho.character.Character
import com.raphael.carvalho.character.Characters

interface CharacterApi {
    val implementation: CharacterApi get() = CharacterApiImp()

    suspend fun listCharacters(page: Long): Characters

    suspend fun getCharacter(id: Long): Character
}
