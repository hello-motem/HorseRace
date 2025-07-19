package com.hellomotem.horserace.race.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.hellomotem.horserace.race.data.repository.RaceRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RaceTimerBroadcastReceiver: BroadcastReceiver() {

    @Inject
    lateinit var raceRepository: RaceRepository

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Some_tag", "received new intent $intent")
        if (intent.action == STOP_TIMER_INTENT_ACTION_NAME) {
            coroutineScope.launch { raceRepository.endRace() }
        }
    }
}