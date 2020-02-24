package com.raphael.carvalho.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raphael.carvalho.database.character.CharacterDao
import com.raphael.carvalho.database.character.model.Character
import com.raphael.carvalho.database.util.converter.ListStringConverter

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringConverter::class)
abstract class RickMortyDatabase : RoomDatabase() {

    abstract fun characterRoomDao(): CharacterDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: RickMortyDatabase? = null

        fun getDatabase(context: Context): RickMortyDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RickMortyDatabase::class.java,
                    "rick_morty_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
