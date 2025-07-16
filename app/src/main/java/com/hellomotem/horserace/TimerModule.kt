package com.hellomotem.horserace

import com.hellomotem.horserace.timer.Timer
import com.hellomotem.horserace.timer.TimerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class TimerModule {
    @Binds
    abstract fun bindTimer(timerImpl: TimerImpl): Timer
}