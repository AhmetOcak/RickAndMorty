package com.rickandmorty.core.common

sealed interface Response<out T> {
    object Loading : Response<Nothing>
    class Success<T>(val data: T) : Response<T>
    class Error<T>(val message: String) : Response<T>
}