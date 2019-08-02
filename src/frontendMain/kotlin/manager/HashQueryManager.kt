package lt.petuska.hazelcast.explorer.manager

import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*
import org.w3c.dom.url.*
import kotlin.browser.*

object HashQueryManager {
  init {
    window.addEventListener("hashchange", { loadStateFromHashQuery() }, null)
    GlobalScope.launch {
      delay(250)
      loadStateFromHashQuery()
      store.subscribe { updateHashQuery() }
    }
  }

  private fun loadStateFromHashQuery() {
    val hashParams = URLSearchParams(window.location.hash.substringAfter("?"))
    HashProperty.values().forEach {
      hashParams.get(it.hashKey).let { hp ->
        it.setAction.invoke(store.getState(), hp)?.let { a -> store.dispatch(a) }
      }
    }
  }

  fun buildHashLink(vararg properties: Pair<HashProperty, String>): String {
    val params = URLSearchParams(window.location.hash.substringAfter("?"))
    properties.toMap().forEach {
      params.set(it.key.hashKey, it.value)
    }
    return "#?$params"
  }

  private fun updateHashQuery() {
    val state = store.getState()
    val hashParams = URLSearchParams()
    HashProperty.values().forEach {
      it.selector.invoke(state)?.let { p -> hashParams.set(it.hashKey, p) }
    }
    window.location.hash = "?$hashParams"
  }

  enum class HashProperty(
      val hashKey: String,
      val selector: HZEState.() -> String?,
      val setAction: HZEState.(String?) -> HZEAction?
  ) {
    ENVIRONMENT(
        "env",
        { selectedEnvironment?.name },
        { HZEAction.SelectEnvironment(environments?.find { e -> e.name == it }) }),
    TARGET(
        "tar",
        { selectedTarget?.name },
        { HZEAction.SelectTarget(selectedEnvironment?.targets?.find { t -> t.name == it }) }),
    EXPLORE_TYPE(
        "exploreType",
        { selectedExploreType?.name },
        {
          it?.let { p ->
            HZEAction.SelectExploreType(ExploreType.valueOf(p))
          }
        }
    )
  }
}