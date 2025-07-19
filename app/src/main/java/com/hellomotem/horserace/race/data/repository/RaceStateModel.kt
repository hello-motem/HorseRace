package com.hellomotem.horserace.race.data.repository

import com.hellomotem.horserace.race.data.RaceTime

sealed interface RaceStateModel {
    data object RaceNotStarted: RaceStateModel

    @JvmInline
    value class RaceInProgress(val raceTime: RaceTime): RaceStateModel

    @JvmInline
    value class RaceEnded(val raceTime: RaceTime): RaceStateModel
}

val RaceStateModel.timeOrDefault: RaceTime get() = when(this) {
    is RaceStateModel.RaceEnded -> raceTime
    is RaceStateModel.RaceInProgress -> raceTime
    RaceStateModel.RaceNotStarted -> RaceTime.ZERO
}