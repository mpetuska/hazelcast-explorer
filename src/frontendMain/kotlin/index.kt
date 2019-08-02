package lt.petuska.hazelcast.explorer

import kotlinext.js.*
import lt.petuska.hazelcast.explorer.component.app.*
import lt.petuska.hazelcast.explorer.manager.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.service.meta.*
import lt.petuska.hazelcast.explorer.service.util.*
import react.dom.*
import react.redux.*
import redux.*
import kotlin.browser.*


@Suppress("UnsafeCastFromDynamic")
val store = createStore<HZEState, RAction, dynamic>(
    combinedReducers(),
    HZEState(),
    compose(
        rEnhancer(),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)

fun main() {
  imports()
  initialisation()
  window.onload = {
    render(document.getElementById("root")) {
      provider(store) {
        app {}
      }
    }
  }
}

private fun imports() {
  @Suppress("UnsafeCastFromDynamic")
  require("css/global.css")
  window.asDynamic().`$` = require("jquery")
  require("bootstrap")
  require("animate.css")
  require("bootstrap-notify")
}

private fun initialisation() {
  HzeConfigService.get().then {
    store.dispatch(HZEAction.HZEConfigLoaded(it))
  }.catch {
    NotificationService.error("Unable to load the configuration from server")
  }

  HashQueryManager
  LocalStorageManager
}