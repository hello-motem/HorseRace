package com.hellomotem.horserace.timer

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface Timer {
    val timerState: Flow<State>

    fun start()

    fun stop()

    fun stopAndReset()

    @JvmInline
    value class State(val value: Duration) {

        companion object {
            val ZERO = State(Duration.ZERO)
        }
    }
}