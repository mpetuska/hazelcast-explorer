package lt.petuska.hazelcast.explorer.manager

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import org.w3c.dom.*
import kotlin.browser.*

object LocalStorageManager : PersistenceManager(PersistentProperty.values().toList()) {
  override fun load(prop: PersistentProperty) = window.localStorage[prop.key]

  override fun save(key: String, value: String?) {
    value?.let {
      window.localStorage[key] = it
    } ?: window.localStorage.removeItem(key)
  }
}