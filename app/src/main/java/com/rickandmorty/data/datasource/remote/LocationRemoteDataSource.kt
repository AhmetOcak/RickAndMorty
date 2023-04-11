package com.rickandmorty.data.datasource.remote

import com.rickandmorty.data.datasource.remote.location.entity.LocationDto

interface LocationRemoteDataSource {

    suspend fun getLocations() : LocationDto
}