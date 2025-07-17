package com.hellomotem.horserace.history.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "race_history")
data class RaceHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "date_of_race")
    val dateOfRace: String,
    @ColumnInfo (name = "race_time")
    val raceTime: String,
)