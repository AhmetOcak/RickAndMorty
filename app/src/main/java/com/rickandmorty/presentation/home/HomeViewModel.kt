package com.rickandmorty.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmorty.core.common.Response
import com.rickandmorty.domain.usecase.location.GetLocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    init {
        getLocations()
    }

    private fun getLocations() = viewModelScope.launch(Dispatchers.IO) {
        getLocationsUseCase().collect() { response ->
            when(response) {
                is Response.Loading -> {}
                is Response.Success -> {}
                is Response.Error -> {}
            }
        }
    }
}