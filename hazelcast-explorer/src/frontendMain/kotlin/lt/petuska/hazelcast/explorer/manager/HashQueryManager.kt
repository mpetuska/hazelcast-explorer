package lt.petuska.hazelcast.explorer.manager

import lt.petuska.hazelcast.explorer.domain.enumerator.PersistentProperty
import org.w3c.dom.url.URLSearchParams
import kotlin.browser.window

object HashQueryManager : PersistenceManager(
  listOf(
    PersistentProperty.ENVIRONMENT,
    PersistentProperty.TARGET,
    PersistentProperty.EXPLORE_TYPE,
    PersistentProperty.HTTP_METHOD,
    PersistentProperty.SELECTED_MAP,
    PersistentProperty.SELECTED_TOPIC,
    PersistentProperty.INSERTED_KEY
  )
) {
  override fun setupSubscriptions(delay: Long) {
    window.addEventListener("hashchange", { loadPersistedState() }, null)
    super.setupSubscriptions()
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