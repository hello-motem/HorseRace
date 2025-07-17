package com.hellomotem.horserace

import com.hellomotem.horserace.race.data.RaceRepository
import com.hellomotem.horserace.race.data.RaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRaceRepository(repository: RaceRepositoryImpl): RaceRepository
}