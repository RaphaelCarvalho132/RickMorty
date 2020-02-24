package com.raphael.carvalho.database.character

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raphael.carvalho.database.RickMortyDatabase
import com.raphael.carvalho.database.character.model.Character
import com.raphael.carvalho.database.character.model.CharacterLocation
import com.raphael.carvalho.database.character.model.CharacterOrigin
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
class CharacterDaoTest {
    private lateinit var characterDao: CharacterDao
    private lateinit var db: RickMortyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RickMortyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        characterDao = db.characterRoomDao()
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
        val initialSize = characterDao.getCharacters().size

        characterDao.insert(characters = listOf(character))

        val characters = characterDao.getCharacters()
        assertEquals(0, initialSize)
        assertEquals(1, characters.size)
        assertEquals(character, characters[0])
    }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWithCharacterId1_whenInsertCharacterId1_thenDatabaseNeedUpdateCharacter() =
        runBlocking {
            val initialCharacter = createCharacter(1)
            characterDao.insert(characters = listOf(initialCharacter))
            val initialSize = characterDao.getCharacters().size
            val character = createCharacter(1, "Raphael Carvalho")

            characterDao.insert(characters = listOf(character))

            val characters = characterDao.getCharacters()
            assertEquals(1, initialSize)
            assertEquals(1, characters.size)
            assertEquals(character, characters[0])
        }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWithCharacterId1_whenGetCharacterId1_thenWillBeReturnedCharacterId1() =
        runBlocking {
            val initialCharacter = createCharacter(1)
            characterDao.insert(characters = listOf(initialCharacter))

            val character = characterDao.getCharacter(1)

            assertEquals(initialCharacter, character)
        }

    @Test
    @Throws(Exception::class)
    fun givenEmptyDatabase_whenGetCharacterId1_thenWillBeReturnedNull() = runBlocking {
        val character = characterDao.getCharacter(1)

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
            characterDao.insert(initialCharacters)

            val characters = characterDao.getCharacters()

            assertEquals(initialCharacters, characters)
        }

    @Test
    @Throws(Exception::class)
    fun givenEmptyDatabase_whenGetCharacters_thenWillBeReturnedEmptyList() = runBlocking {
        val characters = characterDao.getCharacters()

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
        characterDao.insert(initialCharacters)
        val initialSize = characterDao.getCharacters().size

        characterDao.deleteAll()

        assertEquals(3, initialSize)
        assertTrue(characterDao.getCharacters().isEmpty())
    }

    private fun createCharacter(id: Long, name: String = "Raphael") = Character(
        id,
        name,
        "",
        "",
        "",
        "",
        "",
        "",
        CharacterLocation(
            "",
            ""
        ),
        CharacterOrigin(
            "",
            ""
        ),
        emptyList(),
        ""
    )
}