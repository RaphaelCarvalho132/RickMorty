package com.raphael.carvalho.api.character

import com.raphael.carvalho.api.RetrofitBuilder
import com.raphael.carvalho.api.new
import com.raphael.carvalho.api.test.ApiRequestMockServer
import com.raphael.carvalho.character.Character
import com.raphael.carvalho.character.Characters
import com.raphael.carvalho.character.Location
import com.raphael.carvalho.character.Origin
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

@Extensions(
    ExtendWith(ApiRequestMockServer::class)
)
internal class CharacterApiImpTest {
    private val characterApi: CharacterApi = CharacterApiImp(
        RetrofitBuilder(
            ApiRequestMockServer.baseUrl,
            ApiRequestMockServer.httpClient
        ).new()
    )

    @Test
    fun `Given that there is no request error, When list character from page 1, Then should be returned page info and character 16 in the list`() =
        runBlocking {
            ApiRequestMockServer.addRequest(
                200,
                "listCharacters/charactersPage1Example.json"
            )

            val characters =
                characterApi.listCharacters(1)

            Assertions.assertEquals(
                Characters(
                    2,
                    listOf(createCharacter())
                ), characters
            )
        }

    @Test
    fun `Given that there is no request error, When get character id 16, Then should be returned character 16`() =
        runBlocking {
            ApiRequestMockServer.addRequest(
                200,
                "getCharacter/character16.json"
            )

            val character = characterApi.getCharacter(16)

            Assertions.assertEquals(createCharacter(), character)
        }

    private fun createCharacter() = Character(
        16,
        "Amish Cyborg",
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
