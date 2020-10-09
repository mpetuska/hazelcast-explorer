package lt.petuska.hazelcast.explorer.util

import dev.fritz2.remote.getBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Response

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

internal inline fun <reified T> String.parse() = Json.decodeFromString<T>(this)
internal suspend inline fun <reified T> Response.parseBody() = getBody().parse<T>()
