package lt.petuska.hazelcast.explorer.service

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lt.petuska.hazelcast.explorer.domain.HazelcastClient
import lt.petuska.hazelcast.explorer.domain.Target
import java.util.concurrent.ConcurrentHashMap

internal object HazelcastClientService {
  private val clients = ConcurrentHashMap<String, HazelcastClientSubscription>()
  private const val gracePeriodMS = 90000L

  fun getClient(target: Target): HazelcastClient = runBlocking {
    (
      clients[target.id] ?: HazelcastClientSubscription(async { target.client!!.invoke() }).also {
        clients[target.id] = it
        GlobalScope.launch {
          delay(gracePeriodMS)
          if (System.currentTimeMillis() - it.lastRequestTimestamp > gracePeriodMS) {
            clients.remove(target.id)
            it.hazelcastClient.await().shutdown()
          }
        }
      }
      ).let {
      it.lastRequestTimestamp = System.currentTimeMillis()
      it.hazelcastClient.await()
    }
  }

  private data class HazelcastClientSubscription(
    val hazelcastClient: Deferred<HazelcastClient>,
    var lastRequestTimestamp: Long = System.currentTimeMillis()
  )
}
