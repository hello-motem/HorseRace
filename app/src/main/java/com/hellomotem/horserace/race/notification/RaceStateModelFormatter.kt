package com.hellomotem.horserace.race.notification

import android.content.Context
import com.hellomotem.horserace.R
import com.hellomotem.horserace.race.data.repository.RaceStateModel
import com.hellomotem.horserace.race.data.repository.timeOrDefault

class RaceStateModelFormatter(
    context: Context //better be resourceProvider or so instead of raw context
) {
    private val daysString = context.getString(R.string.notification_format_days)
    private val hoursString = context.getString(R.string.notification_format_hours)
    private val minutesString = context.getString(R.string.notification_format_minutes)
    private val secondsString = context.getString(R.string.notification_format_seconds)

    fun format(raceStateModel: RaceStateModel): String = raceStateModel
            .timeOrDefault
            .duration
            .toComponents { days, hours, minutes, seconds, nanoseconds ->
                when {
                    days != 0L -> "$daysString $days, $hoursString $hours, $minutesString $minutes, $secondsString $seconds"
                    hours != 0 -> "$hoursString $hours, $minutesString $minutes, $secondsString $seconds"
                    minutes != 0 -> "$minutesString $minutes, $secondsString $seconds"
                    seconds != 0 -> "$secondsString $seconds"
                    else -> toString()
        }
    }

    fun emptyPlaceholder(): String = "$secondsString 0"
}