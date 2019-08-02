package lt.petuska.hazelcast.explorer.service

import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import kotlin.js.*

object MetaService : FetchService() {
  fun fetchHZEConfig(): Promise<HazelcastExplorerConfigDTO> {
    return GlobalScope.promise {
      get<HazelcastExplorerConfigDTO>(Route.META)
    }
  }
}