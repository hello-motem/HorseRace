package com.hellomotem.horserace.history.data.local.datasource

import com.hellomotem.horserace.history.data.local.model.RaceHistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: RaceHistoryDao
): LocalDataSource {
    override fun getRaceHistory(): Flow<List<RaceHistoryEntity>> = dao
        .getAllRaceHistory()

    override suspend fun saveRaceHistoryEntity(raceHistoryEntity: RaceHistoryEntity) = dao
        .saveRaceHistoryEntity(raceHistoryEntity)

    override suspend fun deleteRaceHistoryEntity(id: String) = dao
        .deleteRaceHistoryEntity(id)
}