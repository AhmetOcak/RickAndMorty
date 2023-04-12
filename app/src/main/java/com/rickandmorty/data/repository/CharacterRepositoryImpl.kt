package com.rickandmorty.data.repository

import com.rickandmorty.data.datasource.remote.character.CharacterRemoteDataSource
import com.rickandmorty.data.mappers.toCharacter
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource
) : CharacterRepository {

    override suspend fun getCharacters(ids: ArrayList<Int>): ArrayList<Character> =
        remoteDataSource.getCharacters(ids).toCharacter()
}