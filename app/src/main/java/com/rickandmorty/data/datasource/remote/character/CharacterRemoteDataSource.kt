package com.rickandmorty.data.datasource.remote.character

import com.rickandmorty.data.datasource.remote.character.entity.CharacterDto

interface CharacterRemoteDataSource {

    suspend fun getCharacters(ids: ArrayList<Int>) : ArrayList<CharacterDto>
}