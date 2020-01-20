package lt.petuska.hazelcast.explorer.service.meta

import io.ktor.client.request.get
import lt.petuska.hazelcast.explorer.domain.HzeConfigDTO
import lt.petuska.hazelcast.explorer.service.FetchService
import lt.petuska.hazelcast.explorer.util.promise

object HzeConfigService : FetchService() {
  fun get() = promise {
    httpClient.get<HzeConfigDTO>("/api/meta/hzeConfig")
  }
}