package com.rickandmorty.data.datasource.remote.location.api

import com.rickandmorty.data.datasource.remote.location.entity.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("api/location")
    suspend fun getLocations(@Query("page") page: Int): Location
}