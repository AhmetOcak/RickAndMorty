package com.rickandmorty.core.di

import com.rickandmorty.data.datasource.remote.character.CharacterRemoteDataSource
import com.rickandmorty.data.datasource.remote.character.CharacterRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindCharacterRemoteDataSource(characterRemoteDataSourceImpl: CharacterRemoteDataSourceImpl): CharacterRemoteDataSource
}