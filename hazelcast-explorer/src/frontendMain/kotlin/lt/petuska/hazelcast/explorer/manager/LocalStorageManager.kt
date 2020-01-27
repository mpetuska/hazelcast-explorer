package lt.petuska.hazelcast.explorer.manager

import kotlinx.serialization.json.Json
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import org.w3c.dom.get
import org.w3c.dom.set
import redux.Store
import redux.WrapperAction
import kotlin.browser.window

object LocalStorageManager {
  private const val key = "hzeConfig"
  fun load() = window.localStorage[key]?.let {
    Json.parse(HzeState.serializer(), it)
  }
  
  fun setupSubscriptions(store: Store<HzeState, HzeAction, WrapperAction>) {
    store.subscribe {
      window.localStorage[key] = Json.stringify(HzeState.serializer(), store.getState())
    }
  }
}