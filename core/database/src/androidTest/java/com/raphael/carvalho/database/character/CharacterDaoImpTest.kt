package com.raphael.carvalho.database.character

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raphael.carvalho.character.Character
import com.raphael.carvalho.character.Location
import com.raphael.carvalho.character.Origin
import com.raphael.carvalho.database.RickMortyDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CharacterDaoImpTest {
    private lateinit var characterRoomDao: CharacterDao
    private lateinit var db: RickMortyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RickMortyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        characterRoomDao = CharacterDaoImp(
            db.characterRoomDao()
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun givenEmptyDatabase_whenInsertCharacter_thenCharacterWillBeSaved() = runBlocking {
        val character = createCharacter(1)
        val initialSize = characterRoomDao.getCharacters().size

        characterRoomDao.insert(listOf(character))

        val characters = characterRoomDao.getCharacters()
        assertEquals(0, initialSize)
        assertEquals(1, characters.size)
        assertEquals(character, characters[0])
    }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWithCharacterId1_whenInsertCharacterId1_thenDatabaseNeedUpdateCharacter() =
        runBlocking {
            val initialCharacter = createCharacter(1)
            characterRoomDao.insert(listOf(initialCharacter))
            val initialSize = characterRoomDao.getCharacters().size
            val character = createCharacter(1, "Raphael Carvalho")

            characterRoomDao.insert(listOf(character))

            val characters = characterRoomDao.getCharacters()
            assertEquals(1, initialSize)
            assertEquals(1, characters.size)
            assertEquals(character, characters[0])
        }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWithCharacterId1_whenGetCharacterId1_thenWillBeReturnedCharacterId1() =
        runBlocking {
            val initialCharacter = createCharacter(1)
            characterRoomDao.insert(listOf(initialCharacter))

            val character = characterRoomDao.getCharacter(1)

            assertEquals(initialCharacter, character)
        }

    @Test
    @Throws(Exception::class)
    fun givenEmptyDatabase_whenGetCharacterId1_thenWillBeReturnedNull() = runBlocking {
        val character = characterRoomDao.getCharacter(1)

        assertNull(character)
    }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWith3Character_whenGetCharacters_thenWillBeReturnedAllCharacters() =
        runBlocking {
            val initialCharacters = listOf(
                createCharacter(1),
                createCharacter(2),
                createCharacter(3)
            )
            characterRoomDao.insert(initialCharacters)

            val characters = characterRoomDao.getCharacters()

            assertEquals(initialCharacters, characters)
        }

    @Test
    @Throws(Exception::class)
    fun givenEmptyDatabase_whenGetCharacters_thenWillBeReturnedEmptyList() = runBlocking {
        val characters = characterRoomDao.getCharacters()

        assertTrue(characters.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWith3Character_whenDeleteAll_thenAllCharactersWillBeDeleted() = runBlocking {
        val initialCharacters = listOf(
            createCharacter(1),
            createCharacter(2),
            createCharacter(3)
        )
        characterRoomDao.insert(initialCharacters)
        val initialSize = characterRoomDao.getCharacters().size

        characterRoomDao.deleteAll()

        assertEquals(3, initialSize)
        assertTrue(characterRoomDao.getCharacters().isEmpty())
    }

    private fun createCharacter(id: Long, name: String = "Raphael") = Character(
        id,
        name,
        "Dead",
        "Alien",
        "Parasite, Cyborg",
        "Male",
        Origin(
            "unknown",
            null
        ),
        Location(
            "Earth (Replacement Dimension)",
            20
        ),
        "https://rickandmortyapi.com/api/character/avatar/16.jpeg",
        listOf(
            15
        ),
        "2017-11-04T21:12:45.235Z"
    )
}