package lt.petuska.hazelcast.explorer

import kotlinext.js.require
import lt.petuska.hazelcast.explorer.component.app.app
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeReducers
import lt.petuska.hazelcast.explorer.redux.HzeState
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

val store = createStore<HzeState, RAction, WrapperAction>(
  HzeReducers(),
  HzeState(),
  compose(
    rEnhancer(),
    @Suppress("UnsafeCastFromDynamic")
    js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
  )
).unsafeCast<Store<HzeState, HzeAction, WrapperAction>>()

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
    store.dispatch(HzeAction.HzeConfigLoaded(it))
    lateinit var tmp: () -> Unit
    tmp = store.subscribe {
      if (store.getState().loaded) {
        tmp()
        render(document.getElementById("root")) {
          provider(store) {
            app {}
          }
        }
      }
    }
  }.catch {
    NotificationService.error("Unable to load the configuration from server")
  }
}