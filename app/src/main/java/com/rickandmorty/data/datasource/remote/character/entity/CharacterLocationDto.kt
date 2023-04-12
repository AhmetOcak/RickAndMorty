package com.rickandmorty.data.datasource.remote.character.entity

import com.google.gson.annotations.SerializedName

data class CharacterLocationDto(
    @SerializedName("name")
    val locationName: String? = null,

    @SerializedName("url")
    val url: String? = null
)
