package com.rickandmorty.domain.repository

import com.rickandmorty.domain.model.character.Character

interface CharacterRepository {

    suspend fun getCharacters(ids: ArrayList<Int>): ArrayList<Character>
}