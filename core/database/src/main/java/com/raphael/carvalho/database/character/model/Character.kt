package com.raphael.carvalho.database.character.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Character.TABLE_NAME)
data class Character(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,
    val name: String,
    val image: String,
    val gender: String,
    val species: String,
    val status: String,
    val type: String,
    val created: String,
    @Embedded val characterLocation: CharacterLocation,
    @Embedded val characterOrigin: CharacterOrigin,
    val episode: List<String>,
    val url: String
) {
    companion object {
        internal const val TABLE_NAME: String = "Character"
        internal const val COLUMN_ID: String = "id"
    }
}