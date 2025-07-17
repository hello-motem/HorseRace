package com.hellomotem.horserace.delegates

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.stringRes(@StringRes resId: Int) = lazy(LazyThreadSafetyMode.NONE) {
    resources.getString(resId)
}