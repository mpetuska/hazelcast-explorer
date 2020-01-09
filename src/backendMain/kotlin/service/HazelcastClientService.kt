package lt.petuska.hazelcast.explorer.service

import com.hazelcast.core.*
import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.*
import java.util.concurrent.*

object HazelcastClientService {
  private val clients = ConcurrentHashMap<String, HazelcastClientSubscription>()
  private const val gracePeriodMS = 90000L

  fun getClient(target: TargetDescriptor): HazelcastInstance = runBlocking {
    (clients[target.toKey()] ?: HazelcastClientSubscription(async { target.client() }).also {
      clients[target.toKey()] = it
      GlobalScope.launch {
        delay(gracePeriodMS)
        if (System.currentTimeMillis() - it.lastRequestTimestamp > gracePeriodMS) {
          clients.remove(target.toKey())
          it.hazelcastInstance.await().shutdown()
        }
      }
    }).let {
      it.lastRequestTimestamp = System.currentTimeMillis()
      it.hazelcastInstance.await()
    }
  }

  private fun TargetDescriptor.toKey() = "[$environment]-[$name]"

  private data class HazelcastClientSubscription(
      val hazelcastInstance: Deferred<HazelcastInstance>,
      var lastRequestTimestamp: Long = System.currentTimeMillis()
  )
}