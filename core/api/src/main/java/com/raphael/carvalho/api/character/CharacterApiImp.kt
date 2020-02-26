package com.raphael.carvalho.api.character

import com.raphael.carvalho.api.RetrofitBuilder
import com.raphael.carvalho.api.character.model.CharacterVo
import com.raphael.carvalho.api.character.model.CharactersVo
import com.raphael.carvalho.api.new
import com.raphael.carvalho.character.Character
import com.raphael.carvalho.character.Characters
import com.raphael.carvalho.character.Location
import com.raphael.carvalho.character.Origin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CharacterApiImp(
    private val characterApi: CharacterRetrofitApi = RetrofitBuilder().new()
) : CharacterApi {

    override suspend fun getCharacter(id: Long) = withContext(Dispatchers.IO) {
        val character = characterApi.getCharacter(id)
        withContext(Dispatchers.Default) {
            character.toCharacter()
        }
    }

    override suspend fun listCharacters(page: Long) = withContext(Dispatchers.IO) {
        val characters = characterApi.listCharacters(page)
        withContext(Dispatchers.Default) {
            characters.toCharacters()
        }
    }
}

private val String.id: Long?
    get() = substringAfterLast('/').toLongOrNull()

private val String.pageNumber: Long?
    get() = substringAfterLast("page=").toLongOrNull()

private fun CharactersVo.toCharacters() = Characters(
    info.next.pageNumber,
    characters.map { characterVo -> characterVo.toCharacter() }
)

private fun CharacterVo.toCharacter() = Character(
    id,
    name,
    status,
    species,
    type,
    gender,
    Origin(
        origin.name,
        origin.url.id
    ),
    Location(
        location.name,
        location.url.id
    ),
    image,
    episode.mapNotNull { ep -> ep.id },
    created
)
