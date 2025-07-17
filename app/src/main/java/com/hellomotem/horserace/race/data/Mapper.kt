package com.hellomotem.horserace.race.data

import com.hellomotem.horserace.timer.Timer

fun Timer.State.toRaceTime(): RaceTime = RaceTime.create(value)