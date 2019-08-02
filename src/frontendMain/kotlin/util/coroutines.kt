package lt.petuska.hazelcast.explorer.util

import kotlinx.coroutines.*

fun <T> promise(init: suspend CoroutineScope.() -> T) = GlobalScope.promise { init() }
fun delay(ms: Long, block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch {
  delay(ms)
  block()
}

fun Any.println(any: Any?) = kotlin.io.println("[${this::class.simpleName}] $any")
fun Any.print(any: Any?) = kotlin.io.print("[${this::class.simpleName}] $any")