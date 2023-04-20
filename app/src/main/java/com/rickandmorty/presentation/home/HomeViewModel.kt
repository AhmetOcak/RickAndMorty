package com.rickandmorty.presentation.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rickandmorty.R
import com.rickandmorty.core.common.Response
import com.rickandmorty.domain.repository.LocationRepository
import com.rickandmorty.domain.usecase.character.GetCharacterUseCase
import com.rickandmorty.presentation.utils.CharacterGender
import com.rickandmorty.presentation.utils.EXCEPTION_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _characterState = MutableStateFlow<CharacterState>(CharacterState.Loading)
    val characterState = _characterState.asStateFlow()

    private val _locations = locationRepository.getLocations().cachedIn(viewModelScope)
    val locations = _locations

    fun getCharacters(residents: ArrayList<String>) {
        viewModelScope.launch(ioDispatcher) {
            val characterIds = arrayListOf<Int>()

            // In order to get the characters from the API, we extract the character ids from the residents URL.
            residents.forEach {
                try {
                    characterIds.add(
                        extractCharacterId(it)
                    )
                } catch (e: Exception) {
                    _characterState.value = CharacterState.Error(message = EXCEPTION_MESSAGE)
                }
            }

            // If characterIds is empty, the selected location does not have a resident.
            // If the characterId list is empty, we give an empty arrayList as data.
            // In Home Screen, it will be checked whether this arrayList is empty and a UI will be drawn accordingly.
            if (characterIds.isEmpty()) {
                _characterState.value = CharacterState.Success(data = arrayListOf())
            } else {
                getCharacterUseCase(characterIds)
                    .flowOn(ioDispatcher)
                    .collect() { response ->
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
    }

    fun extractCharacterId(residentUrl: String): Int {
        val uri = Uri.parse(residentUrl)
        return uri.lastPathSegment?.toInt() ?: throw Exception()
    }

    fun setCharacterGenderImage(gender: String): Int {
        return when (gender) {
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
        _characterState.value = CharacterState.Error(message = EXCEPTION_MESSAGE)
    }
}