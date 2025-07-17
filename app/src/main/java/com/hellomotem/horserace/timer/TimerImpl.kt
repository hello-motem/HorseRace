package com.hellomotem.horserace.timer

import com.hellomotem.horserace.coroutines.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import java.time.Instant
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toKotlinDuration

class TimerImpl @Inject constructor(dispatchers: CoroutineDispatchers): Timer {
    private lateinit var startDate: Instant

    private val scope: CoroutineScope = CoroutineScope(dispatchers.default)
    private var timerJob: Job? = null

    private val _timerState = MutableStateFlow(Timer.State.ZERO)
    override val timerState: StateFlow<Timer.State> = _timerState.asStateFlow()

    override fun start() {
        timerJob?.cancel()

        timerJob = tickerFlow(1.milliseconds)
            .onStart { startDate = Instant.now() }
            .map { java.time.Duration.between(startDate, Instant.now()) }
            .map { duration -> Timer.State(duration.toKotlinDuration()) }
            .onEach { timerState -> _timerState.update { timerState } }
            .launchIn(scope)
    }

    override fun stop() {
        timerJob?.cancel()
    }

    override fun stopAndReset() {
        timerJob?.cancel()
        _timerState.update { Timer.State.ZERO }
    }

    override fun getStartDate(): Timer.StartDate = Timer.StartDate(startDate)
}

