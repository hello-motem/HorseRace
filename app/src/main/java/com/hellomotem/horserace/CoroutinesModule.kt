package com.hellomotem.horserace

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class CoroutinesModule {

    @Binds
    abstract fun bindsCoroutineDispatchers(
        coroutineDispatchersImpl: CoroutineDispatchersImpl
    ): CoroutineDispatchers
}