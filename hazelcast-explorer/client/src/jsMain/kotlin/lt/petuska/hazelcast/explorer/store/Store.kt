package lt.petuska.hazelcast.explorer.store

import dev.fritz2.binding.RootStore
import dev.fritz2.lenses.buildLens
import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import lt.petuska.hazelcast.explorer.app.router
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import dev.fritz2.binding.Store as FStore

internal object Store : RootStore<State>(State())

internal object SelectionStore : FStore<Selection> by Store.sub(buildLens("selection", { it.selection }, { p, v -> p.copy(selection = v) })) {
  init {
    syncBy(router.navTo)
  }

  val selectEnvironment = handle<String?> { state, env ->
    state.rebuild(environment = env)
  }
  val selectTarget = handle<Pair<String?, String?>> { state, (env, target) ->
    state.rebuild(environment = env, target = target)
  }
  val selectMap = handle<String?> { state, map ->
    state.rebuild(map = map)
  }
  val selectTopic = handle<String?> { state, topic ->
    state.rebuild(topic = topic)
  }
  val selectExploreType = handle<ExploreType> { state, et ->
    state.copy(exploreType = et)
  }
  val setMapKey = handle<String?> { state, key ->
    state.copy(insertedMapKey = key)
  }
  val clearSelections = handle { Selection() }
}

internal val isDarkTheme by lazy {
  val darkly = document.getElementById("theme-darkly")!!
  val flatly = document.getElementById("theme-flatly")!!
  val syncThemeCss = { it: Boolean ->
    val (active, inactive) = if (it) {
      darkly to flatly
    } else {
      flatly to darkly
    }
    active.removeAttribute("disabled")
    inactive.setAttribute("disabled", "")
  }
  GlobalScope.launch {
    Store.data.collectLatest {
      syncThemeCss(it.isDarkTheme)
    }
  }
  Store.sub(buildLens("isDarkTheme", { it.isDarkTheme }, { p, v -> p.copy(isDarkTheme = v) })).apply {
    syncBy(
      handle {
        syncThemeCss(it)
        LocalState.isDarkTheme = it
        it
      }
    )
  }
}
