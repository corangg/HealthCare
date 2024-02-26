package com.example.healthcare

import com.example.healthcare.Repository.InformationInputRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideInformationInput(): InformationInputRepository {
        return InformationInputRepository()
    }
}