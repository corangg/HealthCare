package com.example.healthcare

import android.content.Context
import com.example.healthcare.DB.ExerciseRoutineDB
import com.example.healthcare.DB.PhsicalInfoDB
import com.example.healthcare.Dao.ExerciseDao
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

    @Singleton
    @Provides
    fun provideExerciseInfoDB(@ApplicationContext context: Context): ExerciseRoutineDB{
        return  ExerciseRoutineDB.getDatabase(context)
    }

    @Provides
    fun provideExerciseInfoDao(exerciseRoutineDB: ExerciseRoutineDB) : ExerciseDao{
        return exerciseRoutineDB.exerciseDao()
    }
}