package com.hellomotem.horserace.di

import com.hellomotem.horserace.coroutines.CoroutineDispatchers
import com.hellomotem.horserace.coroutines.CoroutineDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CoroutinesModule {

    @Binds
    @Singleton
    abstract fun bindsCoroutineDispatchers(
        coroutineDispatchersImpl: CoroutineDispatchersImpl
    ): CoroutineDispatchers
}