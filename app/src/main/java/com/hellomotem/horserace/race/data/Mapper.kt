package com.hellomotem.horserace.race.data

import com.hellomotem.horserace.race.data.repository.RaceStateModel
import com.hellomotem.horserace.timer.Timer

fun Timer.TimerState.toRaceStateModel(): RaceStateModel = when(this) {

    Timer.TimerState.TimerNotStarted -> RaceStateModel.RaceNotStarted
    is Timer.TimerState.TimerStarted -> RaceStateModel.RaceInProgress(
        RaceTime.create(time.value)
    )
    is Timer.TimerState.TimerStopped -> RaceStateModel.RaceEnded(
        RaceTime.create(time.value)
    )
}