package com.hellomotem.horserace.race.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellomotem.horserace.history.data.model.RaceHistoryModel
import com.hellomotem.horserace.history.data.repository.RaceHistoryRepository
import com.hellomotem.horserace.race.data.repository.RaceRepository
import com.hellomotem.horserace.race.data.repository.RaceStateModel
import com.hellomotem.horserace.race.presentation.formatter.DateFormatter
import com.hellomotem.horserace.race.presentation.models.RaceTimeUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RaceViewModel @Inject constructor(
    private val raceRepository: RaceRepository,
    private val historyRepository: RaceHistoryRepository,
    private val formatter: DateFormatter
): ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            raceRepository.raceState.collect { time ->
                _state.update { state ->
                    when (time) {
                        is RaceStateModel.RaceEnded -> state.copy(
                            raceState = State.RaceState.STOPPED,
                            time = RaceTimeUi.create(time.raceTime.duration)
                        )
                        is RaceStateModel.RaceInProgress -> state.copy(
                            time = RaceTimeUi.create(time.raceTime.duration),
                            raceState = State.RaceState.STARTED
                        )
                        RaceStateModel.RaceNotStarted -> state.copy(
                            raceState = State.RaceState.INITIAL
                        )
                    }
                }
            }
        }
    }

    fun dispatchEvent(event: Event) {
        viewModelScope.launch {
            when (event) {
                Event.OnRestartClicked -> onRestartClicked()

                Event.OnStartClicked -> onStartClicked()

                Event.OnSaveClicked -> onSaveClicked()
            }
        }
    }

    private suspend fun onRestartClicked(): Unit = _state.update { state ->
        raceRepository.resetRace()

        state.copy(isItemSaved = false)
    }

    private suspend fun onStartClicked() {
        when (state.value.raceState) {
            State.RaceState.INITIAL -> raceRepository.startRace()

            State.RaceState.STARTED -> raceRepository.endRace()

            else -> Unit
        }
    }

    private suspend fun onSaveClicked(): Unit = _state.update { state ->
        val startDate = raceRepository.getStartDate().toUi()

        val model = RaceHistoryModel(
            dateOfRace = startDate.formatted(formatter),
            raceTime = state.time.formatted()
        )

        historyRepository.saveRace(model)
        state.copy(isItemSaved = true)
    }

    data class State(
        val time: RaceTimeUi = RaceTimeUi.ZERO,
        val raceState: RaceState = RaceState.INITIAL,
        val isItemSaved: Boolean = false
    ) {
        enum class RaceState { INITIAL, STARTED, STOPPED }
    }

    sealed interface Event {
        data object OnStartClicked: Event
        data object OnRestartClicked: Event
        data object OnSaveClicked: Event
    }
}