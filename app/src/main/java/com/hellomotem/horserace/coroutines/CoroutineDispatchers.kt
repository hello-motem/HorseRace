package com.hellomotem.horserace.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface CoroutineDispatchers {
    val main: CoroutineDispatcher
    val mainImmediate: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

class CoroutineDispatchersImpl @Inject constructor(): CoroutineDispatchers {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}