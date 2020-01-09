package lt.petuska.hazelcast.explorer

import kotlinext.js.*
import lt.petuska.hazelcast.explorer.component.app.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.service.meta.*
import lt.petuska.hazelcast.explorer.service.util.*
import react.dom.*
import react.redux.*
import redux.*
import kotlin.browser.*

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