package com.raphael.carvalho.database.character.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raphael.carvalho.character.Character

@Entity(tableName = CharacterVo.TABLE_NAME)
internal data class CharacterVo(
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
    @Embedded val characterLocation: CharacterLocationVo,
    @Embedded val characterOrigin: CharacterOriginVo,
    val episode: List<Long>
) {
    constructor(character: Character) : this(
        character.id,
        character.name,
        character.image,
        character.gender,
        character.species,
        character.status,
        character.type,
        character.created,
        CharacterLocationVo(
            character.location.id,
            character.location.name
        ),
        CharacterOriginVo(
            character.origin.id,
            character.origin.name
        ),
        character.episode
    )

    companion object {
        internal const val TABLE_NAME: String = "Character"
        internal const val COLUMN_ID: String = "id"
    }
}
