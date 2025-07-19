package com.hellomotem.horserace.di

import com.hellomotem.horserace.race.data.repository.RaceRepository
import com.hellomotem.horserace.race.data.repository.RaceRepositoryImpl
import com.hellomotem.horserace.race.presentation.formatter.DateFormatter
import com.hellomotem.horserace.race.presentation.formatter.DateFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RaceModule {

    @Singleton
    @Binds
    abstract fun bindRaceRepository(repository: RaceRepositoryImpl): RaceRepository

    @Binds
    abstract fun bindDateFormatter(dateFormatterImpl: DateFormatterImpl): DateFormatter
}