package com.hellomotem.horserace.race.presentation

import kotlin.time.Duration

@JvmInline
value class RaceTimeUi private constructor(private val value: Duration) {
    private val hours get() = value.inWholeHours.mod(HOURS_IN_DAY)
    private val minutes get() = value.inWholeMinutes.mod(MINUTES_IN_HOUR)
    private val seconds get() = value.inWholeSeconds.mod(SECONDS_IN_MINUTE)
    private val millis get() = value.inWholeMilliseconds.mod(MILLIS_IN_SECOND)

    fun formatted(): String = hours.formatAsTwoDigits() +
            ":${minutes.formatAsTwoDigits()}" +
            ":${seconds.formatAsTwoDigits()}" +
            ":${millis.formatAsThreeDigits()}"

    private fun Int.formatAsTwoDigits(): String =
        if (this < 10) "0$this" else toString()

    private fun Int.formatAsThreeDigits(): String = when {
        this < 10 -> "00$this"
        this < 100 -> "0$this"
        else -> toString()
    }

    companion object {
        fun create(duration: Duration): RaceTimeUi = RaceTimeUi(duration)

        val ZERO = RaceTimeUi(Duration.ZERO)

        private const val HOURS_IN_DAY = 24
        private const val MINUTES_IN_HOUR = 60
        private const val SECONDS_IN_MINUTE = 60
        private const val MILLIS_IN_SECOND = 1000
    }
}