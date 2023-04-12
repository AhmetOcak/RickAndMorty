package com.rickandmorty.core.di

import com.rickandmorty.data.datasource.remote.character.api.CharacterApi
import com.rickandmorty.data.datasource.remote.location.api.LocationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "https://rickandmortyapi.com/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocationApi(): LocationApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterApi(): CharacterApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterApi::class.java)
    }
}