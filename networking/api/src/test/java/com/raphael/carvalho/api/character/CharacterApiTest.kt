package com.raphael.carvalho.api.character

import com.raphael.carvalho.api.RetrofitBuilder
import com.raphael.carvalho.api.character.model.CharacterVo
import com.raphael.carvalho.api.character.model.CharactersVo
import com.raphael.carvalho.api.character.model.LocationVo
import com.raphael.carvalho.api.character.model.OriginVo
import com.raphael.carvalho.api.character.model.PaginationInfoVo
import com.raphael.carvalho.api.new
import com.raphael.carvalho.api.test.ApiRequestMockServer
import com.raphael.carvalho.api.test.ApiRequestMockServer.addRequest
import com.raphael.carvalho.api.test.ApiRequestMockServer.baseUrl
import com.raphael.carvalho.api.test.ApiRequestMockServer.httpClient
import com.raphael.carvalho.api.test.ApiRequestMockServer.server
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

@Extensions(
    ExtendWith(ApiRequestMockServer::class)
)
class CharacterApiTest {
    private val characterRetrofitApi = RetrofitBuilder(
        baseUrl,
        httpClient
    ).new<CharacterApi>()

    @Test
    fun `Given that there is no request error, When list character from page 1, Then should be returned page info and character 16 in the list`() {
        addRequest(
            200,
            "listCharacters/charactersPage1Example.json"
        )

        val characters = runBlocking {
            characterRetrofitApi.listCharacters(1)
        }

        Assertions.assertEquals("/character/?page=1", server.takeRequest().path)
        Assertions.assertEquals(
            CharactersVo(
                PaginationInfoVo(
                    493,
                    25,
                    "https://rickandmortyapi.com/api/character/?page=2",
                    ""
                ),
                listOf(createCharacter())
            ), characters
        )
    }

    @Test
    fun `Given that there is no request error, When get character id 16, Then should be returned character 16`() {
        addRequest(
            200,
            "getCharacter/character16.json"
        )

        val character = runBlocking {
            characterRetrofitApi.getCharacter(16)
        }

        Assertions.assertEquals("/character/16", server.takeRequest().path)
        Assertions.assertEquals(createCharacter(), character)
    }

    private fun createCharacter() = CharacterVo(
        16,
        "Amish Cyborg",
        "Dead",
        "Alien",
        "Parasite, Cyborg",
        "Male",
        OriginVo(
            "unknown",
            ""
        ),
        LocationVo(
            "Earth (Replacement Dimension)",
            "https://rickandmortyapi.com/api/location/20"
        ),
        "https://rickandmortyapi.com/api/character/avatar/16.jpeg",
        listOf(
            "https://rickandmortyapi.com/api/episode/15"
        ),
        "https://rickandmortyapi.com/api/character/16",
        "2017-11-04T21:12:45.235Z"
    )
}
