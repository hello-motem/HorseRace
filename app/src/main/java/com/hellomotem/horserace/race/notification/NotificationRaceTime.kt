package com.hellomotem.horserace.race.notification

@JvmInline
value class NotificationRaceTime(val value: String) {
    override fun toString(): String = value
}