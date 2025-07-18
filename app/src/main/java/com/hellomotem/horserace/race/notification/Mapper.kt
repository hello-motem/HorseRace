package com.hellomotem.horserace.race.notification

import com.hellomotem.horserace.race.data.RaceTime

fun RaceTime.toNotification(): NotificationRaceTime = NotificationRaceTime(
    value = value.toComponents { days, hours, minutes, seconds, nanoseconds ->
        when {
            days != 0L -> "Дни: $days, Часы: $hours, Минуты: $minutes, Секунды: $seconds"
            hours != 0 -> "Часы: $hours, Минуты: $minutes, Секунды: $seconds"
            minutes != 0 -> "Минуты: $minutes, Секунды: $seconds"
            seconds != 0 -> "Секунды: $seconds"
            else -> toString()
        }
    }
)