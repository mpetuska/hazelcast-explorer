package lt.petuska.hazelcast.explorer.app.page

import dev.fritz2.binding.handledBy
import dev.fritz2.dom.html.HtmlElements
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lt.petuska.hazelcast.explorer.app.router
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.store.SelectionStore

internal fun HtmlElements.home(hidden: Flow<Boolean>) = div("container") {
  hidden.bindAttr("hidden")
  ul("nav nav-tabs nav-fill mb-3") {
    ExploreType.values().map {
      val hid = router.map { r ->
        when (it) {
          ExploreType.MAP -> r.target?.maps.isNullOrEmpty()
          ExploreType.TOPIC -> r.target?.topics.isNullOrEmpty()
        }
      }
      buildTab(it, hid)
      it to hid
    }.toMap()
  }
  map(router.map { it.exploreType != ExploreType.MAP })
  topic(router.map { it.exploreType != ExploreType.TOPIC })
}

private fun HtmlElements.buildTab(exploreType: ExploreType, hidden: Flow<Boolean>) = li("nav-item") {
  attr("role", "button")
  hidden.bindAttr("hidden")
  a("nav-link") {
    className = router.map { if (it.exploreType == exploreType) "active" else "" }
    clicks.map { exploreType } handledBy SelectionStore.selectExploreType
    +"${exploreType.displayName} Explorer"
  }
}
