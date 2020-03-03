package com.raphael.carvalho.database.character.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raphael.carvalho.character.Character

@Entity(tableName = CharacterPo.TABLE_NAME)
data class CharacterPo(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    override val id: Long,
    override val name: String,
    override val status: String,
    override val species: String,
    override val type: String,
    override val gender: String,
    @Embedded override val origin: OriginPo,
    @Embedded override val location: LocationPo,
    override val image: String,
    override val episode: List<Long>,
    override val created: String
) : Character {
    companion object {
        internal const val TABLE_NAME: String = "Character"
        internal const val COLUMN_ID: String = "id"
    }
}
