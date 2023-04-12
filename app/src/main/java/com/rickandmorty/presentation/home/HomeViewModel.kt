package com.rickandmorty.presentation.home

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmorty.R
import com.rickandmorty.core.common.Response
import com.rickandmorty.domain.model.location.Location
import com.rickandmorty.domain.usecase.character.GetCharacterUseCase
import com.rickandmorty.domain.usecase.location.GetLocationsUseCase
import com.rickandmorty.presentation.utils.CharacterGender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val exceptionMessage = "Something went wrong. Please try again later."

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _locationState = MutableStateFlow<LocationsState>(LocationsState.Loading)
    val locationsState = _locationState.asStateFlow()

    private val _characterState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val characterState = _characterState.asStateFlow()

    private var locations: Location? = null

    var selectedLocationId by mutableStateOf(1)
        private set

    fun updateSelectedLocationId(newLocationId: Int) {
        selectedLocationId = newLocationId
    }

    init {
        getLocations()
    }

    private fun getLocations() = viewModelScope.launch(Dispatchers.IO) {
        getLocationsUseCase().collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _locationState.value = LocationsState.Loading
                }
                is Response.Success -> {
                    _locationState.value = LocationsState.Success(data = response.data)
                    locations = response.data

                    getCharacters()
                }
                is Response.Error -> {
                    _locationState.value = LocationsState.Error(message = response.message)
                }
            }
        }
    }

    fun getCharacters() = viewModelScope.launch(Dispatchers.IO) {
        val characterIds = arrayListOf<Int>()

        locations?.results?.find {
            it.id == selectedLocationId
        }?.residents?.forEach {
            try {
                characterIds.add(
                    extractCharacterId(it)
                )
            } catch (e: Exception) {
                _characterState.value = CharacterState.Error(message = exceptionMessage)
            }
        }

        Log.d("locations", locations.toString())
        Log.d("character ids", characterIds.toString())

        // Eğer characterIds boş ise, seçili lokasyonda resident yok demektir.
        if (characterIds.isEmpty()) {
            _characterState.value = CharacterState.Success(data = arrayListOf())
        } else {
            getCharacterUseCase(characterIds).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _characterState.value = CharacterState.Loading
                    }
                    is Response.Success -> {
                        _characterState.value = CharacterState.Success(data = response.data)
                    }
                    is Response.Error -> {
                        _characterState.value = CharacterState.Error(message = response.message)
                    }
                }
            }
        }
    }

    private fun extractCharacterId(residentUrl: String): Int {
        val uri = Uri.parse(residentUrl)
        return uri.lastPathSegment?.toInt() ?: throw Exception()
    }

    fun setCharacterGenderImage(gender: String): Int {
        return when(gender) {
            CharacterGender.MALE -> {
                R.drawable.male
            }
            CharacterGender.FEMALE -> {
                R.drawable.female
            }
            CharacterGender.GENDERLESS -> {
                R.drawable.genderless
            }
            CharacterGender.UNKNOWN -> {
                R.drawable.unknown
            }
            else -> {
                R.drawable.unknown
            }
        }
    }
}