package com.hellomotem.horserace

import com.hellomotem.horserace.database.HorseRaceDatabase
import com.hellomotem.horserace.history.data.local.datasource.LocalDataSource
import com.hellomotem.horserace.history.data.local.datasource.LocalDataSourceImpl
import com.hellomotem.horserace.history.data.local.datasource.RaceHistoryDao
import com.hellomotem.horserace.history.data.repository.RaceHistoryRepository
import com.hellomotem.horserace.history.data.repository.RaceHistoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RaceHistoryModule {

    @Provides
    @Singleton
    fun provideRaceHistoryDao(database: HorseRaceDatabase): RaceHistoryDao =
        database.raceHistoryDao()

    @Provides
    @Singleton
    fun provideRaceHistoryRepository(
        localDataSource: LocalDataSource
    ): RaceHistoryRepository = RaceHistoryRepositoryImpl(
        localDataSource = localDataSource
    )

    @Provides
    @Singleton
    fun provideRaceHistoryLocalDataSource(
        dao: RaceHistoryDao
    ): LocalDataSource = LocalDataSourceImpl(
        dao
    )
}