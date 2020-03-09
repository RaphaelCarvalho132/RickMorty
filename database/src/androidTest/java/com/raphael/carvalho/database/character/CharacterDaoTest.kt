package com.raphael.carvalho.database.character

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raphael.carvalho.database.RickMortyDatabase
import com.raphael.carvalho.database.character.model.CharacterPo
import com.raphael.carvalho.database.character.model.LocationPo
import com.raphael.carvalho.database.character.model.OriginPo
import com.raphael.carvalho.livedata.test.getValueForTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var characterDao: CharacterDao
    private lateinit var db: RickMortyDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RickMortyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        characterDao = db.characterDao()
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
        val initialSize = characterDao.getCharacters().getValueForTest()!!.size

        characterDao.insert(listOf(character))

        val characters = characterDao.getCharacters().getValueForTest()!!
        assertEquals(0, initialSize)
        assertEquals(1, characters.size)
        assertEquals(character, characters[0])
    }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWithCharacterId1_whenInsertCharacterId1_thenDatabaseNeedUpdateCharacter() =
        runBlocking {
            val initialCharacter = createCharacter(1)
            characterDao.insert(listOf(initialCharacter))
            val initialSize = characterDao.getCharacters().getValueForTest()!!.size
            val character = createCharacter(1, "Raphael Carvalho")

            characterDao.insert(listOf(character))

            val characters = characterDao.getCharacters().getValueForTest()!!
            assertEquals(1, initialSize)
            assertEquals(1, characters.size)
            assertEquals(character, characters[0])
        }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWithCharacterId1_whenGetCharacterId1_thenWillBeReturnedCharacterId1() =
        runBlocking {
            val initialCharacter = createCharacter(1)
            characterDao.insert(listOf(initialCharacter))

            val character = characterDao.getCharacter(1).getValueForTest()!!

            assertEquals(initialCharacter, character)
        }

    @Test
    @Throws(Exception::class)
    fun givenEmptyDatabase_whenGetCharacterId1_thenWillBeReturnedNull() = runBlocking {
        val character = characterDao.getCharacter(1)

        assertNull(character.value)
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

            val characters = characterDao.getCharacters().getValueForTest()!!

            assertEquals(initialCharacters, characters)
        }

    @Test
    @Throws(Exception::class)
    fun givenEmptyDatabase_whenGetCharacters_thenWillBeReturnedEmptyList() = runBlocking {
        val characters = characterDao.getCharacters().getValueForTest()!!

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
        val initialSize = characterDao.getCharacters().getValueForTest()!!.size

        characterDao.deleteAll()

        assertEquals(3, initialSize)
        assertTrue(characterDao.getCharacters().getValueForTest()!!.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun givenDatabaseWith2Character_whenListCharacterById_thenAllCharactersSortedById() =
        runBlocking {
            val charactersInDb = listOf(
                createCharacter(2),
                createCharacter(1)
            )
            val charactersSorted = listOf(
                createCharacter(1),
                createCharacter(2)
            )
            val startedListCharacters = characterDao.listCharacterById()

            characterDao.insert(charactersInDb)
            val endedListCharacters = characterDao.listCharacterById()

            characterDao.deleteAll()

            assertTrue(startedListCharacters.isEmpty())
            assertEquals(charactersSorted, endedListCharacters)
        }

    private fun createCharacter(id: Int, name: String = "Raphael") = CharacterPo(
        id,
        name,
        "",
        "",
        "",
        "",
        OriginPo(
            1,
            ""
        ),
        LocationPo(
            null,
            ""
        ),
        "",
        emptyList(),
        ""
    )
}
