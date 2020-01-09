package lt.petuska.hazelcast.explorer.util

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

suspend fun <T> List<T>.asChannel() = GlobalScope.produce {
  forEach { send(it) }
}