package com.hellomotem.horserace

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.hellomotem.horserace.race.data.repository.RaceRepository
import com.hellomotem.horserace.race.data.repository.RaceStateModel
import com.hellomotem.horserace.race.notification.AppLifecycleListener
import com.hellomotem.horserace.race.notification.RaceTimerService
import com.hellomotem.horserace.utils.buildinfo.isAtLeast26
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var appLifecycleListener: AppLifecycleListener

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleListener)
    }
}
