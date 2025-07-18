package com.hellomotem.horserace.utils.buildinfo

import android.os.Build

val isAtLeast26: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
val isAtLeast33: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
val isAtLeast34: Boolean get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE