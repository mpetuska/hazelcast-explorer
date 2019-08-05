package lt.petuska.hazelcast.explorer.manager

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.util.*
import org.w3c.dom.url.*
import kotlin.browser.*

object HashQueryManager : PersistenceManager(listOf(
    PersistentProperty.ENVIRONMENT,
    PersistentProperty.TARGET,
    PersistentProperty.EXPLORE_TYPE
)) {
  override fun setupSubscriptions(delay: Long) {
    window.addEventListener("hashchange", { loadPersistedState() }, null)
    delay(100) {
      super.setupSubscriptions()
    }
  }

  override fun shouldLoad() = window.location.hash.substringAfter("?").isNotBlank()

  override fun load(prop: PersistentProperty) = URLSearchParams(window.location.hash.substringAfter("?")).get(prop.key)

  override fun save(key: String, value: String?) {
    URLSearchParams(window.location.hash.substringAfter("?")).apply {
      value?.let {
        set(key, it)
      } ?: delete(key)
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