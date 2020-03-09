package com.raphael.carvalho.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.raphael.carvalho.api.character.CharacterApi
import com.raphael.carvalho.api.character.model.CharacterVo
import com.raphael.carvalho.database.character.CharacterDao
import com.raphael.carvalho.database.character.model.CharacterPo
import com.raphael.carvalho.database.character.model.LocationPo
import com.raphael.carvalho.database.character.model.OriginPo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultCharacterRepository(
    private val characterDao: CharacterDao,
    private val characterApi: CharacterApi
) : CharacterRepository {
    var currentPage: Int = PAGINATION_UNKNOWN

    override suspend fun listCharacters(): LiveData<List<Character>> =
        characterDao.getCharacters().map { characters -> characters }

    override suspend fun pageCharacters() = withContext(Dispatchers.Default) {
        val pageNum = when (currentPage) {
            PAGINATION_UNKNOWN -> calculatePagination()
            FINISH_PAGINATION -> {
                return@withContext
            }
            else -> currentPage
        }

        val characters = characterApi.listCharacters(pageNum)
        currentPage = characters.info.next.pageNumber ?: FINISH_PAGINATION

        characterDao.insert(
            characters.characters.map { characterVo ->
                characterVo.toCharacterPo()
            }
        )
    }

    @Synchronized
    private suspend fun calculatePagination(): Int {
        if (currentPage != PAGINATION_UNKNOWN) return currentPage

        val characters = characterDao.listCharacterById()

        for (currentPage in 1..characters.size / PAGINATION_SIZE) {
            if (!characters.isLocalAllCharacters(fromPage = currentPage))
                return currentPage
        }

        return 1
    }

    override suspend fun getCharacter(id: Int) =
        characterDao.getCharacter(id).map { characterPo -> characterPo as Character }

    override suspend fun refreshCharacter(id: Int) {
        val character = characterApi.getCharacter(id)
        characterDao.insert(listOf(character.toCharacterPo()))
    }

    companion object {
        const val PAGINATION_UNKNOWN = 0
        const val FINISH_PAGINATION = -1

        const val PAGINATION_SIZE = 20
    }
}

private fun List<CharacterPo>.isLocalAllCharacters(fromPage: Int) =
    get(fromPage.lastIndex).id == fromPage.lastId

private val Int.lastId: Int get() = this
private val Int.lastIndex: Int get() = this - 1

private val String.pageNumber: Int?
    get() = substringAfterLast("page=").toIntOrNull()

private fun CharacterVo.toCharacterPo() = CharacterPo(
    id,
    name,
    status,
    species,
    type,
    gender,
    OriginPo(
        origin.url.id,
        origin.name
    ),
    LocationPo(
        location.url.id,
        location.name
    ),
    image,
    episode.mapNotNull { ep -> ep.id },
    created
)

private val String.id: Long?
    get() = substringAfterLast('/').toLongOrNull()

fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}
