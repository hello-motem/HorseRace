package com.hellomotem.horserace.history.data.local.datasource

import com.hellomotem.horserace.history.data.local.model.RaceHistoryEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getRaceHistory(): Flow<List<RaceHistoryEntity>>

    suspend fun saveRaceHistoryEntity(raceHistoryEntity: RaceHistoryEntity)

    suspend fun deleteRaceHistoryEntity(id: String)
}