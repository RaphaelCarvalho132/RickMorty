package com.raphael.carvalho.database.character

import com.raphael.carvalho.character.Character
import com.raphael.carvalho.character.Location
import com.raphael.carvalho.character.Origin
import com.raphael.carvalho.database.character.model.CharacterVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CharacterDaoImp(
    private val characterDao: CharacterRoomDao
) : CharacterDao {
    override suspend fun insert(characters: List<Character>) = withContext(Dispatchers.Default) {
        val charactersVo = characters.map { character -> CharacterVo(character) }
        withContext(Dispatchers.IO) {
            characterDao.insert(charactersVo)
        }
    }

    override suspend fun getCharacter(id: Long) = withContext(Dispatchers.IO) {
        val characterVo = characterDao.getCharacter(id)
        withContext(Dispatchers.Default) {
            characterVo?.toCharacter()
        }
    }

    override suspend fun getCharacters() = withContext(Dispatchers.IO) {
        val charactersVo = characterDao.getCharacters()
        withContext(Dispatchers.Default) {
            charactersVo.map { characterVo -> characterVo.toCharacter() }
        }
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        characterDao.deleteAll()
    }
}

private fun CharacterVo.toCharacter() = Character(
    id,
    name,
    status,
    species,
    type,
    gender,
    Origin(
        characterOrigin.originName,
        characterOrigin.originId
    ),
    Location(
        characterLocation.locationName,
        characterLocation.locationId
    ),
    image,
    episode,
    created
)
