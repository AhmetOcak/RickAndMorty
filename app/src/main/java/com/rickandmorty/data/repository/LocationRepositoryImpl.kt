package com.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rickandmorty.data.datasource.remote.location.LocationPagingDataSource
import com.rickandmorty.data.datasource.remote.location.entity.Results
import com.rickandmorty.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationPagingDataSource: LocationPagingDataSource
) : LocationRepository {

    override fun getLocations(): Flow<PagingData<Results>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { locationPagingDataSource }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}