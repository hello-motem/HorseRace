package com.hellomotem.horserace.race.presentation

import com.hellomotem.horserace.race.data.RaceStartDate
import com.hellomotem.horserace.race.presentation.models.RaceStartDateUi

fun RaceStartDate.toUi(): RaceStartDateUi = RaceStartDateUi.create(value)