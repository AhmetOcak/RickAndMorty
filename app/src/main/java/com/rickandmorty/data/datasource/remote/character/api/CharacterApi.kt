package com.rickandmorty.data.datasource.remote.character.api

import com.rickandmorty.data.datasource.remote.character.entity.CharacterDto
import com.rickandmorty.domain.model.character.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {

    @GET("api/character/{ids}")
    suspend fun getCharacters(@Path("ids") ids: ArrayList<Int>): ArrayList<CharacterDto>
}