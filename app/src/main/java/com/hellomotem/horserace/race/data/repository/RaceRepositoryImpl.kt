package com.hellomotem.horserace.race.data.repository

import com.hellomotem.horserace.coroutines.CoroutineDispatchers
import com.hellomotem.horserace.race.data.RaceStartDate
import com.hellomotem.horserace.race.data.RaceTime
import com.hellomotem.horserace.race.data.toRaceTime
import com.hellomotem.horserace.timer.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class RaceRepositoryImpl @Inject constructor(
    private val timer: Timer,
    dispatchers: CoroutineDispatchers
): RaceRepository {
    override val raceTime: StateFlow<RaceTime> = timer
        .timerState
        .map { timerState -> timerState.toRaceTime() }
        .stateIn(CoroutineScope(dispatchers.default), SharingStarted.Eagerly, RaceTime.ZERO)

    override suspend fun startRace() {
        timer.start()
    }

    override suspend fun endRace() {
        timer.stop()
    }

    override suspend fun resetRace() {
        timer.stopAndReset()
    }

    override suspend fun getStartDate(): RaceStartDate {
        val timerStartDate = timer.getStartDate()
        return RaceStartDate(timerStartDate.value)
    }
}