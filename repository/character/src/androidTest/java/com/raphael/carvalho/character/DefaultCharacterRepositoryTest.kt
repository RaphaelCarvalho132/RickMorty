package com.raphael.carvalho.character

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.raphael.carvalho.api.RetrofitBuilder
import com.raphael.carvalho.api.character.CharacterApi
import com.raphael.carvalho.api.new
import com.raphael.carvalho.api.test.ApiRequestMockServer
import com.raphael.carvalho.api.test.ApiRequestMockServer.baseUrl
import com.raphael.carvalho.api.test.ApiRequestMockServer.httpClient
import com.raphael.carvalho.database.RickMortyDatabase
import com.raphael.carvalho.database.character.model.CharacterPo
import com.raphael.carvalho.database.character.model.LocationPo
import com.raphael.carvalho.database.character.model.OriginPo
import com.raphael.carvalho.livedata.test.getValueForTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DefaultCharacterRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val characterRetrofitApi = RetrofitBuilder(
        baseUrl,
        httpClient
    ).new<CharacterApi>()
    private lateinit var db: RickMortyDatabase

    private lateinit var repository: CharacterRepository

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RickMortyDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        repository = DefaultCharacterRepository(
            db.characterDao(),
            characterRetrofitApi
        )
    }

    @After
    @Throws(IOException::class)
    fun after() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun givenEmptyDb_whenMakePagination_thenFirstPageCharactersWillBeLoaded() = runBlocking {
        ApiRequestMockServer.addRequest(
            200,
            "listCharacters/charactersPage1Example.json"
        )
        val characters = repository.listCharacters()
        val startedCharacters = characters.getValueForTest()!!

        repository.pageCharacters()

        val endedCharacters = characters.getValueForTest()!!
        assertEquals(0, startedCharacters.size)
        assertEquals(1, endedCharacters.size)
        assertEquals(createCharacter(16), endedCharacters[0])
    }

    @Test
    @Throws(Exception::class)
    fun givenPreExistingDb_whenMakePagination_thenNextPageCharactersWillBeLoaded() = runBlocking {
        ApiRequestMockServer.addRequest(
            200,
            "listCharacters/charactersPage1Example.json"
        )
        val characters = repository.listCharacters()
        val startedCharacters = characters.getValueForTest()!!

        repository.pageCharacters()

        val endedCharacters = characters.getValueForTest()!!
        assertEquals(0, startedCharacters.size)
        assertEquals(1, endedCharacters.size)
        assertEquals(createCharacter(16), endedCharacters[0])
    }

    private fun createCharacter(id: Int) = CharacterPo(
        id,
        "Amish Cyborg",
        "Dead",
        "Alien",
        "Parasite, Cyborg",
        "Male",
        OriginPo(
            null,
            "unknown"
        ),
        LocationPo(
            20,
            "Earth (Replacement Dimension)"
        ),
        "https://rickandmortyapi.com/api/character/avatar/16.jpeg",
        listOf(
            15
        ),
        "2017-11-04T21:12:45.235Z"
    )

    companion object {
        private val apiRequestMockServer = ApiRequestMockServer

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            apiRequestMockServer.beforeClass()
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            apiRequestMockServer.afterClass()
        }
    }
}
