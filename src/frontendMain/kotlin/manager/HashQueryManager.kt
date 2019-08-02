package lt.petuska.hazelcast.explorer.manager

import lt.petuska.hazelcast.explorer.domain.*
import org.w3c.dom.url.*
import kotlin.browser.*

object HashQueryManager : PersistenceManager(listOf(
    PersistentProperty.ENVIRONMENT,
    PersistentProperty.TARGET,
    PersistentProperty.EXPLORE_TYPE
)) {
  override fun setupSubscriptions() {
    window.addEventListener("hashchange", { loadPersistedState() }, null)
    super.setupSubscriptions()
  }

  override fun load(prop: PersistentProperty) = URLSearchParams(window.location.hash.substringAfter("?")).get(prop.key)

  override fun save(key: String, value: String) {
    URLSearchParams(window.location.hash.substringAfter("?")).apply {
      set(key, value)
    }.let { window.location.hash = "?$it" }
  }

  fun buildHashLink(vararg properties: Pair<PersistentProperty, String>): String {
    return URLSearchParams(window.location.hash.substringAfter("?")).apply {
      properties.forEach {
        set(it.first.key, it.second)
      }
    }.let { "#?$it" }
  }
}