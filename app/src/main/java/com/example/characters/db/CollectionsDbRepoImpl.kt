package com.example.characters.db

import kotlinx.coroutines.flow.Flow

class CollectionsDbRepoImpl(private val characterDao: CharacterDao) : CollectionDbRepo {
    override suspend fun getCharactersFromRepo(): Flow<List<DbCharacter>> =
        characterDao.getCharacters()

    override suspend fun getCharacterFromRepo(characterId: Int): Flow<DbCharacter> =
        characterDao.getCharacter(characterId)

    override suspend fun addCharacterFromRepo(character: DbCharacter) =
        characterDao.addCharacter(character)

    override suspend fun updateCharacterFromRepo(character: DbCharacter) =
        characterDao.updateCharacter(character)


    override suspend fun deleteCharacterFromRepo(character: DbCharacter) =
        characterDao.deleteCharacter(character)

}