package com.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.rickandmorty.data.datasource.remote.location.entity.Results
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    /**
     * This method provide Rick And Morty locations from the API.
     */
    fun getLocations(): Flow<PagingData<Results>>
}