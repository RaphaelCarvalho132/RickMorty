package com.raphael.carvalho.database.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raphael.carvalho.database.character.model.Character
import com.raphael.carvalho.database.character.model.Character.Companion.COLUMN_ID
import com.raphael.carvalho.database.character.model.Character.Companion.TABLE_NAME

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<Character>)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    suspend fun getCharacter(id: Long): Character?

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getCharacters(): List<Character>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()
}
