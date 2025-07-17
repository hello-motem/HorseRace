package com.hellomotem.horserace.timer

import android.os.Build
import androidx.annotation.RequiresApi
import com.hellomotem.horserace.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import java.time.Instant
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toKotlinDuration

class TimerImpl @Inject constructor(dispatchers: CoroutineDispatchers): Timer {
    private var startDate: Instant? = null

    private val scope: CoroutineScope = CoroutineScope(dispatchers.default)
    private var timerJob: Job? = null

    private val _timerState = MutableStateFlow(Timer.State.ZERO)
    override val timerState: StateFlow<Timer.State> = _timerState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun start() {
        timerJob?.cancel()

        timerJob = tickerFlow(1.milliseconds)
            .onStart { startDate = Instant.now() }
            .map { java.time.Duration.between(startDate, Instant.now()) }
            .map { duration -> Timer.State(duration.toKotlinDuration()) }
            .onEach { timerState -> _timerState.update { timerState } }
            .launchIn(scope)

        timerJob?.start()
    }

    override fun stop() {
        timerJob?.cancel()
    }

    override fun stopAndReset() {
        timerJob?.cancel()
        _timerState.update { Timer.State.ZERO }
    }

    private fun tickerFlow(period: Duration): Flow<Unit> = flow {
        while (true) {
            emit(Unit)
            delay(period)
        }
    }
}
