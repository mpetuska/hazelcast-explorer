package lt.petuska.hazelcast.explorer.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise

internal fun <T> promise(init: suspend CoroutineScope.() -> T) = GlobalScope.promise { init() }

internal fun launch(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch { block() }

internal fun launchConditional(condition: Boolean, block: suspend CoroutineScope.() -> Unit) {
  if (condition) GlobalScope.launch { block() }
}

internal fun delay(ms: Long, block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch {
  delay(ms)
  block()
}

internal fun Any.println(any: Any?) = kotlin.io.println("[${this::class.simpleName}] $any")

internal fun Any.print(any: Any?) = kotlin.io.print("[${this::class.simpleName}] $any")
