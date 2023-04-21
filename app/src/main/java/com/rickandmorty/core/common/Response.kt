package com.rickandmorty.core.common

/**
 * Base API response type
 * @param T type of Response
 */
sealed interface Response<out T> {
    object Loading : Response<Nothing>
    class Success<T>(val data: T) : Response<T>
    class Error<T>(val message: String) : Response<T>
}