package com.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.rickandmorty.data.datasource.remote.location.entity.Results
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun getLocations(): Flow<PagingData<Results>>
}