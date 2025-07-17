package com.hellomotem.horserace.race.presentation.formatter

import java.time.Instant

interface DateFormatter {
    fun format(instant: Instant): String
}