package lt.petuska.hazelcast.explorer.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise

fun <T> promise(init: suspend CoroutineScope.() -> T) = GlobalScope.promise { init() }

fun launch(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch { block() }

fun launchConditional(condition: Boolean, block: suspend CoroutineScope.() -> Unit) {
  if (condition) GlobalScope.launch { block() }
}

fun delay(ms: Long, block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch {
  delay(ms)
  block()
}

fun Any.println(any: Any?) = kotlin.io.println("[${this::class.simpleName}] $any")

fun Any.print(any: Any?) = kotlin.io.print("[${this::class.simpleName}] $any")