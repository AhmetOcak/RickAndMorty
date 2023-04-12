package com.rickandmorty.data.datasource.remote.location

import com.rickandmorty.data.datasource.remote.location.api.LocationApi
import com.rickandmorty.data.datasource.remote.location.entity.LocationDto
import javax.inject.Inject

class LocationRemoteDataSourceImpl @Inject constructor(
    private val api: LocationApi
) : LocationRemoteDataSource {

    override suspend fun getLocations(): LocationDto = api.getLocations()
}