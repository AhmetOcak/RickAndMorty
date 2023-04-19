package com.rickandmorty.data.datasource.remote.character

import com.rickandmorty.data.datasource.remote.character.entity.CharacterDto
import com.rickandmorty.data.datasource.remote.character.entity.CharacterLocationDto
import com.rickandmorty.data.datasource.remote.character.entity.OriginDto
import kotlinx.coroutines.delay

class FakeCharacterRemoteDataSourceImpl : CharacterRemoteDataSource {

    val characterList = arrayListOf<CharacterDto>()

    override suspend fun getCharacters(ids: ArrayList<Int>): ArrayList<CharacterDto> {
        characterList.add(
            CharacterDto(
                id = 0,
                name = "fake name",
                status = "fake status",
                species = "fake species",
                type = "fake type",
                gender = "fake gender",
                origin = OriginDto(
                    name = "fake origin",
                    url = "fake url"
                ),
                location = CharacterLocationDto(
                    locationName = "fake location",
                    url = "fake url"
                ),
                image = "fake image",
                url = "fake url",
                episode = arrayListOf(),
                created = "fake created date"
            )
        )

        delay(1000)

        return characterList
    }
}