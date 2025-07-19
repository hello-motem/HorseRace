package com.hellomotem.horserace.utils

fun <T> T.applyIf(predicate: Boolean, block: T.() -> Unit) =
    if (predicate) apply(block) else this