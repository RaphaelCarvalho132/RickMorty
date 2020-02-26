package com.raphael.carvalho.database.character

import com.raphael.carvalho.character.Character

interface CharacterDao {
    suspend fun insert(characters: List<Character>)

    suspend fun getCharacter(id: Long): Character?

    suspend fun getCharacters(): List<Character>

    suspend fun deleteAll()
}
