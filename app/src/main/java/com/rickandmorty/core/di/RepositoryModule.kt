package com.rickandmorty.core.di

import com.rickandmorty.data.repository.CharacterRepositoryImpl
import com.rickandmorty.data.repository.LocationRepositoryImpl
import com.rickandmorty.domain.repository.CharacterRepository
import com.rickandmorty.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @Singleton
    @Binds
    abstract fun bindCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository
}