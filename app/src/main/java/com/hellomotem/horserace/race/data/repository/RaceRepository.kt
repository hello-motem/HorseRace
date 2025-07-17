package com.hellomotem.horserace.race.data.repository

import com.hellomotem.horserace.race.data.RaceStartDate
import com.hellomotem.horserace.race.data.RaceTime
import kotlinx.coroutines.flow.StateFlow

interface RaceRepository {

    val raceTime: StateFlow<RaceTime>

    suspend fun startRace()

    suspend fun endRace()

    suspend fun resetRace()

    suspend fun getStartDate(): RaceStartDate
}