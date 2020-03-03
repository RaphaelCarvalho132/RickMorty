package com.raphael.carvalho.database.character

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raphael.carvalho.database.character.model.CharacterVo
import com.raphael.carvalho.database.character.model.CharacterVo.Companion.COLUMN_ID
import com.raphael.carvalho.database.character.model.CharacterVo.Companion.TABLE_NAME

@Dao
internal interface CharacterRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterVos: List<CharacterVo>)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    fun getCharacter(id: Long): LiveData<CharacterVo?>

    @Query("SELECT * FROM $TABLE_NAME")
    fun getCharacters(): LiveData<List<CharacterVo>>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()
}
