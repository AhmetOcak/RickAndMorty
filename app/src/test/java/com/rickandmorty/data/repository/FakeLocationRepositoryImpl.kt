package com.rickandmorty.data.repository

import androidx.paging.PagingData
import com.rickandmorty.data.datasource.remote.location.entity.Results
import com.rickandmorty.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocationRepositoryImpl : LocationRepository {
    override fun getLocations(): Flow<PagingData<Results>> {
        return flow {  }
    }
}