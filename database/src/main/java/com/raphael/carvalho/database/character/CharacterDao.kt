package com.raphael.carvalho.database.character

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raphael.carvalho.database.character.model.CharacterPo
import com.raphael.carvalho.database.character.model.CharacterPo.Companion.COLUMN_ID
import com.raphael.carvalho.database.character.model.CharacterPo.Companion.TABLE_NAME

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterPos: List<CharacterPo>)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    fun getCharacter(id: Long): LiveData<CharacterPo?>

    @Query("SELECT * FROM $TABLE_NAME")
    fun getCharacters(): LiveData<List<CharacterPo>>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()
}
