package lt.petuska.hazelcast.explorer.manager

import kotlinx.serialization.json.Json
import lt.petuska.hazelcast.explorer.domain.HzeConfig
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import org.w3c.dom.get
import org.w3c.dom.set
import redux.Store
import redux.WrapperAction
import kotlin.browser.window

object LocalStorageManager {
  private const val key = "hzeConfig"
  private const val versionKey = "hzeConfigVersion"
  fun load(config: HzeConfig) = window.localStorage[key]?.let {
    if (window.localStorage[versionKey] == config.version) {
      Json.parse(HzeState.serializer(), it)
    } else {
      window.localStorage[versionKey] = config.version
      window.localStorage.removeItem(key)
      null
    }
  }
  
  fun setupSubscriptions(store: Store<HzeState, HzeAction, WrapperAction>) {
    store.subscribe {
      window.localStorage[key] = Json.stringify(HzeState.serializer(), store.getState())
    }
  }
}