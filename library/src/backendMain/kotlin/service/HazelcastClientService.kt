package lt.petuska.hazelcast.explorer.service

import com.hazelcast.core.HazelcastInstance
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.TargetDescriptor
import java.util.concurrent.ConcurrentHashMap

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