package lt.petuska.hazelcast.explorer.manager

import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.redux.*
import org.w3c.dom.*
import kotlin.browser.*

object LocalStorageManager {
  init {
    loadStateFromStore()
    store.subscribe { update() }
  }

  private fun loadStateFromStore() {
    LocalStorageProperty.values().forEach {
      window.localStorage[it.key]?.let { value ->
        store.dispatch(it.updater(value))
      }
    }
  }

  private fun update() {
    val state = store.getState()
    LocalStorageProperty.values().forEach {
      it.selector(state)?.let { value ->
        window.localStorage[it.key] = value
      }
    }
  }

  enum class LocalStorageProperty(
    val key: String,
    val selector: HZEState.() -> String?,
    val updater: (String) -> HZEAction
  ) {
    THEME("theme", { "$theme" }, { HZEAction.SetTheme(Theme.valueOf(it)) })
  }
}