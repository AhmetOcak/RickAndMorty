package com.rickandmorty.domain.usecase.character

import com.rickandmorty.core.common.Response
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.domain.repository.CharacterRepository
import com.rickandmorty.domain.utils.ErrorMessages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repository: CharacterRepository) {

    /**
     * This method gets characters from the API.
     *
     * @param ids ids of the characters.
     * @return a [Response] flow of type [Character] list.
     */
    suspend operator fun invoke(ids: ArrayList<Int>): Flow<Response<ArrayList<Character>>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getCharacters(ids)))
        } catch (e: IOException) {
            emit(Response.Error(message = ErrorMessages.INTERNET))
        } catch (e: Exception) {
            emit(Response.Error(message = e.message ?: ErrorMessages.UNKNOWN))
        }
    }
}