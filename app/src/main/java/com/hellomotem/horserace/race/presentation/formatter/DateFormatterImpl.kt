package com.hellomotem.horserace.race.presentation.formatter

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class DateFormatterImpl @Inject constructor(): DateFormatter {

    override fun format(instant: Instant): String = DateTimeFormatter
        .ofPattern(PATTERN, Locale.getDefault())
        .withZone(ZoneId.systemDefault())
        .format(instant)
}

private const val PATTERN = "HH:mm, dd.MM.yyyy"