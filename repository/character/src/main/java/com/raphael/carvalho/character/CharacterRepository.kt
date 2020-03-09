package com.raphael.carvalho.character

import androidx.lifecycle.LiveData

interface CharacterRepository {
    suspend fun listCharacters(): LiveData<List<Character>>

    suspend fun pageCharacters()

    suspend fun getCharacter(id: Int): LiveData<Character>

    suspend fun refreshCharacter(id: Int)
}
