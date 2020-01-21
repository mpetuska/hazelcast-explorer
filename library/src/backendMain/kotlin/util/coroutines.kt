package lt.petuska.hazelcast.explorer.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce

suspend fun <T> List<T>.asChannel() = GlobalScope.produce {
  forEach { send(it) }
}