package com.hellomotem.horserace.timer

import kotlinx.coroutines.flow.Flow
import java.time.Instant
import kotlin.time.Duration

interface Timer {
    val timerState: Flow<State>

    fun start()

    fun stop()

    fun stopAndReset()

    fun getStartDate(): StartDate

    @JvmInline
    value class State(val value: Duration) {

        companion object {
            val ZERO = State(Duration.ZERO)
        }
    }

    @JvmInline
    value class StartDate(val value: Instant)
}