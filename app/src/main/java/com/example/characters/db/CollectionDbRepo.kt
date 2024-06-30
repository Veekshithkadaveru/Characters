package com.example.characters.db

import kotlinx.coroutines.flow.Flow


interface CollectionDbRepo {
    suspend fun getCharactersFromRepo(): Flow<List<DbCharacter>>
    suspend fun getCharacterFromRepo(characterId: Int): Flow<DbCharacter>
    suspend fun addCharacterFromRepo(character: DbCharacter)
    suspend fun updateCharacterFromRepo(character: DbCharacter)
    suspend fun deleteCharacterFromRepo(character: DbCharacter)

}