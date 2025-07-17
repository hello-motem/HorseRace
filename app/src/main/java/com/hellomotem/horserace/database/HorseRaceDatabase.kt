package com.hellomotem.horserace.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hellomotem.horserace.history.data.local.datasource.RaceHistoryDao
import com.hellomotem.horserace.history.data.local.model.RaceHistoryEntity

@Database(entities = [RaceHistoryEntity::class], version = 1)
abstract class HorseRaceDatabase: RoomDatabase() {
    abstract fun raceHistoryDao(): RaceHistoryDao
}