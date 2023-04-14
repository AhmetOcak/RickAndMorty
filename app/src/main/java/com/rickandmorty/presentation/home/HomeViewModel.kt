package com.rickandmorty.presentation.home

import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rickandmorty.R
import com.rickandmorty.core.common.Response
import com.rickandmorty.domain.repository.LocationRepository
import com.rickandmorty.domain.usecase.character.GetCharacterUseCase
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
    private val getCharacterUseCase: GetCharacterUseCase,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _characterState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val characterState = _characterState.asStateFlow()

    private var locations: Location? = null

    init {
        getLocations()
    }

    fun getLocations() = locationRepository.getLocations().cachedIn(viewModelScope)

    fun getCharacters(residents: ArrayList<String>) = viewModelScope.launch(Dispatchers.IO) {
        val characterIds = arrayListOf<Int>()

        residents.forEach {
            try {
                characterIds.add(
                    extractCharacterId(it)
                )
            } catch (e: Exception) {
                _characterState.value = CharacterState.Error(message = exceptionMessage)
            }
        }

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

    fun setCharacterStateError() {
        _characterState.value = CharacterState.Error(message = exceptionMessage)
    }
}