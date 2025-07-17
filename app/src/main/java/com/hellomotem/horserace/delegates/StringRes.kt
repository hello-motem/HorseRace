package com.hellomotem.horserace.delegates

import android.app.Activity
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.stringRes(@StringRes resId: Int) = lazy(LazyThreadSafetyMode.NONE) {
    resources.getString(resId)
}

fun Activity.stringArray(@ArrayRes resId: Int) = lazy(LazyThreadSafetyMode.NONE) {
    resources.getStringArray(resId)
}