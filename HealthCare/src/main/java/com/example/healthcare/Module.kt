package com.example.healthcare

import android.content.Context
import com.example.healthcare.DB.PhsicalInfoDB
import com.example.healthcare.Dao.PhsicalInfoDao
import com.example.healthcare.Repository.InformationInputRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun providePhsicalInfoDb(@ApplicationContext context: Context): PhsicalInfoDB {
        return PhsicalInfoDB.getDatabase(context)
    }

    @Provides
    fun providePhsicalInfoDao(phsicalInfoDB: PhsicalInfoDB): PhsicalInfoDao {
        return phsicalInfoDB.phsicalDao()
    }
}