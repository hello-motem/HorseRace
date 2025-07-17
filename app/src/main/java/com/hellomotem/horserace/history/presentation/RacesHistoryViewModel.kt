package com.hellomotem.horserace.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellomotem.horserace.history.data.model.RaceHistoryModel
import com.hellomotem.horserace.history.data.repository.RaceHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RacesHistoryViewModel @Inject constructor(
    private val repository: RaceHistoryRepository
): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.history
                .map { historyItems -> historyItems.toUi() }
                .collect { historyItems ->
                    _state.update { it.copy(items = historyItems) }
                }
        }
    }

    fun dispatchEvent(event: Event) {
        when (event) {
            is Event.DeleteHistoryItem -> {
                viewModelScope.launch {
                    repository.deleteRaceHistoryItem(event.item.id)
                }
            }
        }
    }

    sealed interface Event {
        class DeleteHistoryItem(val item: RaceHistoryItemUi): Event
    }

    data class State(
        val items: List<RaceHistoryItemUi> = emptyList()
    )
}

fun List<RaceHistoryModel>.toUi(): List<RaceHistoryItemUi> = map { item ->
    RaceHistoryItemUi(
        id = item.id,
        dateOfRace = item.dateOfRace,
        raceTime = item.raceTime
    )
}