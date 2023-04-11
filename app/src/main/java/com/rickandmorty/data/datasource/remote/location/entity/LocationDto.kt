package com.rickandmorty.data.datasource.remote.location.entity

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("info")
    val info: InfoDto?,

    @SerializedName("results")
    val results: ArrayList<ResultsDto> = arrayListOf()
)