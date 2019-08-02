package lt.petuska.hazelcast.explorer.service

import com.hazelcast.core.*
import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.*
import java.util.*
import java.util.concurrent.*

class HazelcastClientService {
  companion object {
    private val clients = ConcurrentHashMap<String, HazelcastClientSubscription>()
    const val gracePeriodMS = 90000L
  }

  fun getClient(target: TargetDescriptor): HazelcastInstance {
    return runBlocking {
      val clientSubscription = clients[target.toKey()] ?: HazelcastClientSubscription(async { target.client() }).also {
        clients[target.toKey()] = it
        GlobalScope.launch {
          delay(gracePeriodMS)
          if (Date().time - it.lastRequestTimestamp > gracePeriodMS) {
            clients.remove(target.toKey())
            it.hazelcastInstance.await().shutdown()
          }
        }
      }

      clientSubscription.lastRequestTimestamp = Date().time
      clientSubscription.hazelcastInstance.await()
    }
  }

  private fun TargetDescriptor.toKey() = "[$environment]-[$name]"

  private data class HazelcastClientSubscription(
    val hazelcastInstance: Deferred<HazelcastInstance>,
    var lastRequestTimestamp: Long = Date().time
  )
}