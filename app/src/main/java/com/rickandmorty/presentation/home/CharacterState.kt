package com.rickandmorty.presentation.home

import com.rickandmorty.domain.model.character.Character

sealed interface CharacterState {
    object Loading : CharacterState
    data class Success(val data: ArrayList<Character>) : CharacterState
    data class Error(val message: String) : CharacterState
}