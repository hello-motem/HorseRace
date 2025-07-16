package com.hellomotem.horserace.race.presentation

data class RaceTimeUi(
    val hours: String,
    val minutes: String,
    val seconds: String,
    val millis: String
) {
    fun formatted(): String = "$hours:$minutes:$seconds:$millis"

    companion object {
        val empty = RaceTimeUi("00", "00", "00", "000")
    }
}