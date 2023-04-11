package com.rickandmorty.domain.usecase.location

import android.util.Log
import com.rickandmorty.core.common.Response
import com.rickandmorty.domain.model.location.Location
import com.rickandmorty.domain.repository.LocationRepository
import com.rickandmorty.domain.utils.ErrorMessages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repository: LocationRepository) {

    suspend operator fun invoke(): Flow<Response<Location>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getLocations()))
        } catch (e: IOException) {
            emit(Response.Error(message = ErrorMessages.INTERNET))
            Log.e("GET_LOCATION_USE_CASE.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(message = e.message ?: ErrorMessages.UNKNOWN))
            Log.e("GET_LOCATION_USE_CASE.kt", e.stackTraceToString())
        }
    }
}