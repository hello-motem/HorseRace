package com.hellomotem.horserace.race.data

import kotlinx.coroutines.flow.StateFlow

interface RaceRepository {

    val raceTime: StateFlow<RaceTime>

    suspend fun startRace()

    suspend fun endRace()

    suspend fun resetRace()

    suspend fun getStartDate(): RaceStartDate?
}