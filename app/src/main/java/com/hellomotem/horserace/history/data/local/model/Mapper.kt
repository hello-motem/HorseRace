package com.hellomotem.horserace.history.data.local.model

import com.hellomotem.horserace.history.data.model.RaceHistoryModel

fun List<RaceHistoryEntity>.toModel(): List<RaceHistoryModel> = map { it.toModel() }

fun RaceHistoryEntity.toModel(): RaceHistoryModel = RaceHistoryModel(
    id = id.toString(),
    dateOfRace = dateOfRace,
    raceTime = raceTime
)

fun RaceHistoryModel.toEntity(): RaceHistoryEntity = RaceHistoryEntity(
    dateOfRace = dateOfRace,
    raceTime = raceTime
)