package lt.petuska.hazelcast.explorer.store

import kotlinx.browser.window
import lt.petuska.hazelcast.explorer.config
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal object LocalState {
  private const val versionKey = "hzeConfigVersion"
  private var clear = (window.localStorage[versionKey] != config.version).also {
    window.localStorage[versionKey] = config.version
  }

  var isDarkTheme: Boolean by StorageDelegate(true, clear) { toBoolean() }
}

private class StorageDelegate<V>(
  private val default: V,
  clear: Boolean,
  private val stringifier: V.() -> String? = { toString() },
  private val converter: String.() -> V
) : ReadWriteProperty<LocalState, V?> {
  private var cleared: Boolean = !clear

  override fun setValue(thisRef: LocalState, property: KProperty<*>, value: V?) {
    value?.let { value.stringifier() }?.let {
      window.localStorage[property.name] = it
    } ?: window.localStorage.removeItem(property.name)
  }

  override fun getValue(thisRef: LocalState, property: KProperty<*>): V = run {
    if (!cleared) {
      setValue(thisRef, property, null)
      cleared = true
    }
    window.localStorage[property.name]?.run(converter) ?: default
  }
}
