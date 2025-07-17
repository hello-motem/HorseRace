package com.hellomotem.horserace.history.data.repository

import com.hellomotem.horserace.history.data.model.RaceHistoryModel
import kotlinx.coroutines.flow.Flow

interface RaceHistoryRepository {

    val history: Flow<List<RaceHistoryModel>>

    suspend fun saveRace(raceHistoryModel: RaceHistoryModel)

    suspend fun deleteRaceHistoryItem(id: String)
}
