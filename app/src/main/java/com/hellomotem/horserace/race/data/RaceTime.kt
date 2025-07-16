package com.hellomotem.horserace.race.data

import com.hellomotem.horserace.timer.Timer
import kotlin.time.Duration

@JvmInline
value class RaceTime private constructor(private val value: Duration) {
    companion object {
        fun create(duration: Duration): RaceTime = RaceTime(duration)

        val ZERO = RaceTime(Duration.ZERO)
    }
}

fun Timer.State.toRaceTime(): RaceTime = RaceTime.create(
    hours = value.inWholeHours.toString(),
    minutes = value.inWholeMinutes.toString(),
    seconds = value.inWholeSeconds.toString(),
    millis = value.inWholeMilliseconds.toString()
)