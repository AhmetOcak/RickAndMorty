package com.rickandmorty.domain.model.character

import android.net.Uri
import com.google.gson.Gson

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: CharacterLocation,
    val image: String,
    val episode: ArrayList<String>,
    val url: String,
    val created: String
) {
    override fun toString(): String = Uri.encode(Gson().toJson(this))
}
