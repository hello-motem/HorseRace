package com.hellomotem.horserace.race.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import com.hellomotem.horserace.R
import com.hellomotem.horserace.race.data.repository.RaceRepository
import com.hellomotem.horserace.utils.buildinfo.isAtLeast26
import com.hellomotem.horserace.utils.buildinfo.isAtLeast34
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class RaceTimerService: Service() {

    @Inject
    lateinit var raceRepository: RaceRepository

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    @OptIn(FlowPreview::class)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        if (isAtLeast34) {
            ServiceCompat.startForeground(
                this,
                1,
                newNotification(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(1, newNotification())
        }
        coroutineScope.launch {
            raceRepository.raceTime
                .distinctUntilChanged { old, new ->
                    old.value.inWholeSeconds == new.value.inWholeSeconds
                }
                .map { it.toNotification().toString() }
                .collect { updateNotification(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun newNotification(text: String = ""): Notification {
        val ACTION_SNOOZE = "snooze"

        val snoozeIntent = Intent(this, RaceTimerBroadcastReceiver::class.java).apply {
            action = ACTION_SNOOZE
            putExtra("EXTRA_NOTIFICATION_ID", 0)
        }

        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.notification_title_text))
            .setContentText(text)
            .setVisibility(VISIBILITY_PUBLIC)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(
                R.drawable.ic_launcher_foreground,
                getString(R.string.stop_button_text),
                snoozePendingIntent
            )
            .addAction(
                R.drawable.ic_launcher_foreground,
                getString(R.string.restart_button_text),
                snoozePendingIntent
            )
            .build()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun updateNotification(text: String) {
        Log.d("Some_tag", "update notification called with $text")
        with(NotificationManagerCompat.from(this))  {
            // notificationId is a unique int for each notification that you must define.
            notify(NOTIFICATION_ID, newNotification(text))
        }
    }

    private fun createNotificationChannel() {
        if (isAtLeast26) {
            val name = CHANNEL_ID//getString(R.string.channel_name)
            val descriptionText = "Sample description"//getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

private const val NOTIFICATION_ID = 1
private const val CHANNEL_ID = "timer_channel"