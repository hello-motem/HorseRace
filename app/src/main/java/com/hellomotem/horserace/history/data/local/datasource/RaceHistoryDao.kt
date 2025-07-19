package com.hellomotem.horserace.history.data.local.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hellomotem.horserace.history.data.local.model.RaceHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceHistoryDao {

    @Query("SELECT * FROM race_history")
    fun getAllRaceHistory(): Flow<List<RaceHistoryEntity>>

    @Query("DELETE FROM race_history WHERE id = :id")
    suspend fun deleteRaceHistoryEntity(id: Long)

    @Query("SELECT EXISTS (SELECT 1 FROM race_history WHERE id = :id)")
    suspend fun isRaceHistoryEntitySaved(id: Long): Boolean

    @Insert
    suspend fun saveRaceHistoryEntity(raceHistoryEntity: RaceHistoryEntity)
}