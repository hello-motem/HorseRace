package com.hellomotem.horserace.race.notification

import android.content.Context
import android.content.Intent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.hellomotem.horserace.race.data.repository.RaceRepository
import com.hellomotem.horserace.race.data.repository.RaceStateModel
import com.hellomotem.horserace.utils.buildinfo.isAtLeast26
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppLifecycleListener @Inject constructor(
    private val raceRepository: RaceRepository,
    @ApplicationContext private val context: Context
): DefaultLifecycleObserver {
    private val isTimerRunning get() = raceRepository
        .raceState.value is RaceStateModel.RaceInProgress

    private var isServiceRunning = false

    private val intent = Intent(context, RaceTimerService::class.java)

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (isServiceRunning) {
            context.stopService(intent)
            isServiceRunning = false
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        if (isTimerRunning) {
            if (isAtLeast26) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
            isServiceRunning = true
        }
    }
}