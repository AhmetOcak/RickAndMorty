package com.rickandmorty.data.datasource.remote.location.api

import com.rickandmorty.data.datasource.remote.location.entity.LocationDto
import retrofit2.http.GET

interface LocationApi {

    @GET("api/location")
    suspend fun getLocations(): LocationDto
}