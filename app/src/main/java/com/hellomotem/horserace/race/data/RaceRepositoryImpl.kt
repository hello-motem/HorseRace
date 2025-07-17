package com.hellomotem.horserace.race.data

import com.hellomotem.horserace.CoroutineDispatchers
import com.hellomotem.horserace.history.data.repository.RaceHistoryRepository
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