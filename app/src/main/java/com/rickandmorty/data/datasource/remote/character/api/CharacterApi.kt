package com.rickandmorty.data.datasource.remote.character.api

import com.rickandmorty.data.datasource.remote.character.entity.CharacterDto
import com.rickandmorty.data.utils.CHARACTER_END_POINT
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {

    @GET(CHARACTER_END_POINT)
    suspend fun getCharacters(
        @Path("ids") ids: ArrayList<Int>
    ): ArrayList<CharacterDto>
}