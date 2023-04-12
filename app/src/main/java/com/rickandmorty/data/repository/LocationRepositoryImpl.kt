package com.rickandmorty.data.repository

import com.rickandmorty.data.datasource.remote.location.LocationRemoteDataSource
import com.rickandmorty.data.mappers.toLocation
import com.rickandmorty.domain.model.location.Location
import com.rickandmorty.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val remoteDataSource: LocationRemoteDataSource
) : LocationRepository {

    override suspend fun getLocations(): Location = remoteDataSource.getLocations().toLocation()
}