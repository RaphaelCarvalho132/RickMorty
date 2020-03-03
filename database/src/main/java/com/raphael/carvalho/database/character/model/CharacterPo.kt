package com.raphael.carvalho.database.character.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raphael.carvalho.character.Character

@Entity(tableName = CharacterPo.TABLE_NAME)
internal data class CharacterPo(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    override val id: Long,
    override val name: String,
    override val image: String,
    override val gender: String,
    override val species: String,
    override val status: String,
    override val type: String,
    override val created: String,
    @Embedded override val location: LocationPo,
    @Embedded override val origin: OriginPo,
    override val episode: List<Long>
) : Character {
    companion object {
        internal const val TABLE_NAME: String = "Character"
        internal const val COLUMN_ID: String = "id"
    }
}
