package com.rickandmorty.data.datasource.remote.location.entity

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("info")
    val info: Info?,

    @SerializedName("results")
    val results: ArrayList<Results> = arrayListOf()
)