package lt.petuska.hazelcast.explorer

import kotlinext.js.require
import lt.petuska.hazelcast.explorer.component.app.app
import lt.petuska.hazelcast.explorer.domain.HzeConfig
import lt.petuska.hazelcast.explorer.manager.LocalStorageManager
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.hzeReducer
import lt.petuska.hazelcast.explorer.service.meta.HzeConfigService
import lt.petuska.hazelcast.explorer.service.util.NotificationService
import react.dom.render
import react.redux.provider
import redux.RAction
import redux.Store
import redux.WrapperAction
import redux.compose
import redux.createStore
import redux.rEnhancer
import kotlin.browser.document
import kotlin.browser.window

lateinit var store: Store<HzeState, HzeAction, WrapperAction>
  private set

lateinit var config: HzeConfig
  private set

fun main() {
  imports()
  initialisation()
}

private fun imports() {
  require("css/global.css")
  window.asDynamic().`$` = require("jquery")
  require("bootstrap")
  require("animate.css")
  require("bootstrap-notify")
}

private fun initialisation() {
  HzeConfigService.get().then {
    config = it
    val state = LocalStorageManager.load(config) ?: HzeState()
    store = createStore<HzeState, RAction, WrapperAction>(
      hzeReducer,
      state,
      compose(
        rEnhancer(),
        @Suppress("UnsafeCastFromDynamic")
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
      )
    ).unsafeCast<Store<HzeState, HzeAction, WrapperAction>>()
    LocalStorageManager.setupSubscriptions(store)
    render(document.getElementById("root")) {
      provider(store) {
        app {}
      }
    }
  }.catch {
    println(it.message)
    NotificationService.error("Unable to load the configuration from server")
  }
}