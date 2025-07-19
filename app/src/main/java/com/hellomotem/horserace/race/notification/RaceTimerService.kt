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
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import com.hellomotem.horserace.R
import com.hellomotem.horserace.main.MainActivity
import com.hellomotem.horserace.race.data.repository.RaceRepository
import com.hellomotem.horserace.race.data.repository.RaceStateModel
import com.hellomotem.horserace.utils.applyIf
import com.hellomotem.horserace.utils.buildinfo.isAtLeast26
import com.hellomotem.horserace.utils.buildinfo.isAtLeast34
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RaceTimerService: Service() {

    @Inject
    lateinit var raceRepository: RaceRepository

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val openAppIntent by lazy(LazyThreadSafetyMode.NONE) {
        PendingIntent.getActivity(
            this,
            0,
            Intent(
                this,
                MainActivity::class.java
            ),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private val stopTimerIntent by lazy(LazyThreadSafetyMode.NONE) {
        val intent = Intent(this, RaceTimerBroadcastReceiver::class.java).apply {
            action = STOP_TIMER_INTENT_ACTION_NAME
        }

        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private val raceStateModelFormatter by lazy(LazyThreadSafetyMode.NONE) {
        RaceStateModelFormatter(this)
    }

    @OptIn(FlowPreview::class)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onCreate() {
        super.onCreate()

        coroutineScope.launch {
            raceRepository.raceState
                .distinctUntilChanged { old, new ->
                    if (old is RaceStateModel.RaceInProgress
                        && new is RaceStateModel.RaceInProgress) {
                        old.raceTime.duration.inWholeSeconds == new.raceTime.duration.inWholeSeconds
                    } else false
                }
                .collect { updateNotification(it) }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            newNotification(),
            if (isAtLeast34) ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE else 0
        )

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun newNotification(
        text: String = raceStateModelFormatter.emptyPlaceholder(),
        isStopButtonVisible: Boolean = true
    ): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.notification_title_text))
            .setContentText(text)
            .setVisibility(VISIBILITY_PUBLIC)
            .setSilent(true)
            .setAutoCancel(true)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .applyIf(isStopButtonVisible) {
                addAction(
                R.drawable.ic_launcher_foreground,
                getString(R.string.stop_button_text),
                    stopTimerIntent
                )
            }
            .setContentIntent(openAppIntent)
            .build()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun updateNotification(raceStateModel: RaceStateModel) {
        with(NotificationManagerCompat.from(this))  {
            notify(
                NOTIFICATION_ID,
                newNotification(
                    text = raceStateModelFormatter.format(raceStateModel),
                    isStopButtonVisible = raceStateModel is RaceStateModel.RaceInProgress
                )
            )
        }
    }

    private fun createNotificationChannel() {
        if (isAtLeast26) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_title_text),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.notification_channel_description)
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

private const val NOTIFICATION_ID = 1
private const val CHANNEL_ID = "timer_notification_channel"
