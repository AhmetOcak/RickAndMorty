package com.rickandmorty.data.datasource.remote.location

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.data.datasource.remote.location.api.LocationApi
import com.rickandmorty.data.datasource.remote.location.entity.Results
import javax.inject.Inject

class LocationPagingDataSource @Inject constructor(private val api: LocationApi) : PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = api.getLocations(page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}