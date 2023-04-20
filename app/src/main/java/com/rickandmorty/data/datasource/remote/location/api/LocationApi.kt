package com.rickandmorty.data.datasource.remote.location.api

import com.rickandmorty.data.datasource.remote.location.entity.Location
import com.rickandmorty.data.utils.LOCATION_END_POINT
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET(LOCATION_END_POINT)
    suspend fun getLocations(@Query("page") page: Int): Location
}