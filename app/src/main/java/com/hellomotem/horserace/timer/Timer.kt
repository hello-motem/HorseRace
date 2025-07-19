package com.hellomotem.horserace.timer

import kotlinx.coroutines.flow.Flow
import java.time.Instant
import kotlin.time.Duration

interface Timer {
    val timerState: Flow<TimerState>

    fun start()

    fun stop()

    fun stopAndReset()

    fun getStartDate(): StartDate


    sealed interface TimerState {
        data object TimerNotStarted: TimerState

        @JvmInline
        value class TimerStarted(val time: Time): TimerState

        @JvmInline
        value class TimerStopped(val time: Time): TimerState
    }
    @JvmInline
    value class Time(val value: Duration)

    @JvmInline
    value class StartDate(val value: Instant)
}