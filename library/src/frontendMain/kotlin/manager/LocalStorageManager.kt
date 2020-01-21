package lt.petuska.hazelcast.explorer.manager

import lt.petuska.hazelcast.explorer.domain.enumerator.PersistentProperty
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.browser.window

object LocalStorageManager : PersistenceManager(PersistentProperty.values().toList()) {
  override fun load(prop: PersistentProperty) = window.localStorage[prop.key]
  
  override fun save(key: String, value: String?) {
    value?.let {
      window.localStorage[key] = it
    } ?: window.localStorage.removeItem(key)
  }
}