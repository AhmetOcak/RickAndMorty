package com.rickandmorty.data.mappers

import com.rickandmorty.data.datasource.remote.character.entity.CharacterDto
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.domain.model.character.CharacterLocation
import com.rickandmorty.domain.model.character.Origin

fun ArrayList<CharacterDto>.toCharacter(): ArrayList<Character> {
    val characters: ArrayList<Character> = arrayListOf()

    forEach {
        characters.add(
            Character(
                id = it.id ?: 0,
                name = it.name ?: "",
                gender = it.gender ?: "",
                location = CharacterLocation(
                    locationName = it.location?.locationName ?: "",
                    url = it.location?.url ?: ""
                ),
                url = it.url ?: "",
                type = it.type ?: "",
                image = it.image ?: "",
                created = it.created ?: "",
                origin = Origin(
                    name = it.origin?.name ?: "",
                    url = it.origin?.url ?: ""
                ),
                species = it.species ?: "",
                status = it.status ?: "",
                episode = it.episode
            )
        )
    }

    return characters
}