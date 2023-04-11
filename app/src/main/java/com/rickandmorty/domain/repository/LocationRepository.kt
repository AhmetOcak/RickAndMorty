package com.rickandmorty.domain.repository

import com.rickandmorty.domain.model.location.Location

interface LocationRepository {

    suspend fun getLocations(): Location
}