package lt.petuska.hazelcast.explorer.service

import com.hazelcast.core.HazelcastInstance
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import java.util.concurrent.ConcurrentHashMap

internal object HazelcastClientService {
  private val clients = ConcurrentHashMap<String, HazelcastClientSubscription>()
  private const val gracePeriodMS = 90000L
  
  fun getClient(target: Target): HazelcastInstance = runBlocking {
    (clients[target.id] ?: HazelcastClientSubscription(async { target.client!!.invoke() }).also {
      clients[target.id] = it
      GlobalScope.launch {
        delay(gracePeriodMS)
        if (System.currentTimeMillis() - it.lastRequestTimestamp > gracePeriodMS) {
          clients.remove(target.id)
          it.hazelcastInstance.await().shutdown()
        }
      }
    }).let {
      it.lastRequestTimestamp = System.currentTimeMillis()
      it.hazelcastInstance.await()
    }
  }
  
  private data class HazelcastClientSubscription(
    val hazelcastInstance: Deferred<HazelcastInstance>,
    var lastRequestTimestamp: Long = System.currentTimeMillis()
  )
}