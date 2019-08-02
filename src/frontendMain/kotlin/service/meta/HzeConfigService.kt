package lt.petuska.hazelcast.explorer.service.meta

import domain.route.*
import lt.petuska.hazelcast.explorer.util.*

object HzeConfigService {
  private val route = HzeConfigRoute
  fun get() = promise {
    route.get("fetch")
  }
}