package com.raphael.carvalho.database.character.model

import androidx.room.ColumnInfo
import com.raphael.carvalho.character.Location

internal data class LocationPo(
    @ColumnInfo(name = "locationId")
    override val id: Long?,
    @ColumnInfo(name = "locationName")
    override val name: String
) : Location
