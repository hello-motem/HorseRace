package com.hellomotem.horserace.race.data.repository

import com.hellomotem.horserace.race.data.RaceTime

sealed interface RaceStateModel {
    data object RaceNotStarted: RaceStateModel

    @JvmInline
    value class RaceInProgress(val raceTime: RaceTime): RaceStateModel

    @JvmInline
    value class RaceEnded(val raceTime: RaceTime): RaceStateModel
}