package com.hellomotem.horserace.race.presentation.models

import com.hellomotem.horserace.race.presentation.formatter.DateFormatter
import java.time.Instant

@JvmInline
value class RaceStartDateUi private constructor(
    private val instant: Instant
) {
    fun formatted(formatter: DateFormatter): String = formatter.format(instant)

    companion object {
        fun create(instant: Instant): RaceStartDateUi = RaceStartDateUi(instant)
    }
}