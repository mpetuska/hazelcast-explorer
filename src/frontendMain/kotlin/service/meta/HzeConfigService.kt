package lt.petuska.hazelcast.explorer.service.meta

import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.service.*
import lt.petuska.hazelcast.explorer.util.*

object HzeConfigService : FetchService() {
  fun get() = promise {
    val call = httpClient.call()

    httpClient.get<HzeConfigDTO>("/api/meta/hzeConfig")
  }
}