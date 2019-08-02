package lt.petuska.hazelcast.explorer

import kotlinext.js.*
import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.component.app.*
import lt.petuska.hazelcast.explorer.manager.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.service.*
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
  @Suppress("UnsafeCastFromDynamic")
  require("css/global.css")
  window.asDynamic().`$` = require("jquery")
  require("bootstrap")
  MetaService.fetchHZEConfig().then {
    store.dispatch(HZEAction.HZEConfigLoaded(it))
  }
  HashQueryManager
  LocalStorageManager
  window.onload = {
    render(document.getElementById("root")) {
      provider(store) {
        app {}
      }
    }
  }
}