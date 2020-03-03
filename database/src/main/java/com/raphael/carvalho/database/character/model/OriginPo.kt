package com.raphael.carvalho.database.character.model

import androidx.room.ColumnInfo
import com.raphael.carvalho.character.Origin

data class OriginPo(
    @ColumnInfo(name = "originId")
    override val id: Long?,
    @ColumnInfo(name = "originName")
    override val name: String
) : Origin
