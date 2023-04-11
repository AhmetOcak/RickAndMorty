package com.rickandmorty.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmorty.core.common.Response
import com.rickandmorty.domain.usecase.location.GetLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    private val _locationState = MutableStateFlow<LocationsState>(LocationsState.Loading)
    val locationsState = _locationState.asStateFlow()

    init {
        getLocations()
    }

    private fun getLocations() = viewModelScope.launch(Dispatchers.IO) {
        getLocationsUseCase().collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _locationState.value = LocationsState.Loading
                }
                is Response.Success -> {
                    _locationState.value = LocationsState.Success(data = response.data)
                }
                is Response.Error -> {
                    _locationState.value = LocationsState.Error(message = response.message)
                }
            }
        }
    }
}