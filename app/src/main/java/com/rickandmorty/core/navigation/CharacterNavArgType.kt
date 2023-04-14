package com.rickandmorty.core.navigation

import com.google.gson.Gson
import com.rickandmorty.core.common.JsonNavType
import com.rickandmorty.domain.model.character.Character

class CharacterNavArgType: JsonNavType<Character>() {

    override fun fromJsonParse(value: String): Character = Gson().fromJson(value, Character::class.java)

    override fun Character.getJsonParse(): String = Gson().toJson(this)
}