package com.rickandmorty.presentation.home

import com.rickandmorty.domain.model.location.Location

sealed interface LocationsState {
    object Loading : LocationsState
    data class Success(val data: Location) : LocationsState
    data class Error(val message: String) : LocationsState
}