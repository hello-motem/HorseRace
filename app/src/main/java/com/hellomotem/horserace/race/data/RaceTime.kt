package com.hellomotem.horserace.race.data

import kotlin.time.Duration

@JvmInline
value class RaceTime private constructor(val value: Duration) {
    companion object {
        fun create(duration: Duration): RaceTime = RaceTime(duration)

        val ZERO = RaceTime(Duration.ZERO)
    }
}
