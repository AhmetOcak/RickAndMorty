package com.rickandmorty.data.datasource.remote.character

import com.rickandmorty.data.datasource.remote.character.api.CharacterApi
import com.rickandmorty.data.datasource.remote.character.entity.CharacterDto
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val api: CharacterApi
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(ids: ArrayList<Int>): ArrayList<CharacterDto> =
        api.getCharacters(ids)
}