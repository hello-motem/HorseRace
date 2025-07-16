package com.hellomotem.horserace.race.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellomotem.horserace.race.data.RaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceViewModel @Inject constructor(
    private val raceRepository: RaceRepository
): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            raceRepository.raceTime.collect { time ->
                Log.d("TAG", "State was collected with $time")
                updateState { state ->
                    state.copy(
                        time = RaceTimeUi.create(time.value)
                    )
                }
            }
        }
    }

    fun dispatchEvent(event: Event) {
        when (event) {
            Event.OnRestartClicked -> {
                updateState { currentState ->
                    currentState.copy(
//                        time = 0,
                        isRaceInProgress = false
                    )
                }
            }

            Event.OnStartClicked -> {
                viewModelScope.launch { raceRepository.startRace() }
            }
        }
    }

    private inline fun updateState(
        crossinline stateUpdater: (State) -> State
    ) {
        viewModelScope.launch {
            _state.update { state -> stateUpdater(state) }
        }
    }

    data class State(
        val time: RaceTimeUi = RaceTimeUi.ZERO,
        val isRaceInProgress: Boolean = false
    )

    sealed interface Event {
        data object OnStartClicked: Event
        data object OnRestartClicked: Event
    }
}