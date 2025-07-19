package com.hellomotem.horserace.history.data.repository

import com.hellomotem.horserace.history.data.local.datasource.LocalDataSource
import com.hellomotem.horserace.history.data.local.model.toEntity
import com.hellomotem.horserace.history.data.local.model.toModel
import com.hellomotem.horserace.history.data.model.RaceHistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RaceHistoryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): RaceHistoryRepository {
    override val history: Flow<List<RaceHistoryModel>> = localDataSource
        .getRaceHistory()
        .map { raceHistoryEntities -> raceHistoryEntities.toModel() }

    override suspend fun saveRace(raceHistoryModel: RaceHistoryModel) = localDataSource
        .saveRaceHistoryEntity(raceHistoryModel.toEntity())

    override suspend fun deleteRaceHistoryItem(id: Long) = localDataSource
        .deleteRaceHistoryEntity(id)
}