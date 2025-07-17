package com.hellomotem.horserace.race.presentation

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
                _state.update { state ->
                    state.copy(time = RaceTimeUi.create(time.value))
                }
            }
        }
    }

    fun dispatchEvent(event: Event) {
        when (event) {
            Event.OnRestartClicked -> {
                viewModelScope.launch {
                    _state.update { state ->

                        raceRepository.resetRace()
                        state.copy(raceState = State.RaceState.INITIAL)
                    }
                }
            }

            Event.OnStartClicked -> {
                viewModelScope.launch {
                    _state.update { state ->
                        state.copy(
                            raceState = when (state.raceState) {
                                State.RaceState.INITIAL -> {
                                    raceRepository.startRace()
                                    State.RaceState.STARTED
                                }
                                State.RaceState.STARTED -> {
                                    raceRepository.endRace()
                                    State.RaceState.STOPPED
                                }
                                State.RaceState.STOPPED -> State.RaceState.STOPPED
                            }
                        )
                    }
                }
            }
        }
    }

    data class State(
        val time: RaceTimeUi = RaceTimeUi.ZERO,
        val raceState: RaceState = RaceState.INITIAL
    ) {
        enum class RaceState { INITIAL, STARTED, STOPPED }
    }

    sealed interface Event {
        data object OnStartClicked: Event
        data object OnRestartClicked: Event
    }
}