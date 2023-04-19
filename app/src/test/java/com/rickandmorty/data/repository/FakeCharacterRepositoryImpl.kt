package com.rickandmorty.data.repository

import com.rickandmorty.data.datasource.remote.character.FakeCharacterRemoteDataSourceImpl
import com.rickandmorty.data.mappers.toCharacter
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.domain.repository.CharacterRepository

class FakeCharacterRepositoryImpl(
    private val fakeCharacterRemoteDataSourceImpl: FakeCharacterRemoteDataSourceImpl
) : CharacterRepository {

    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getCharacters(ids: ArrayList<Int>): ArrayList<Character> {
        return if (shouldReturnError) {
            throw Exception()
        } else {
            fakeCharacterRemoteDataSourceImpl.getCharacters(ids).toCharacter()
        }
    }
}