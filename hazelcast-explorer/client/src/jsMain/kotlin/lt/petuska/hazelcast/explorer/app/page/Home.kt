package lt.petuska.hazelcast.explorer.app.page

import dev.fritz2.binding.handledBy
import dev.fritz2.dom.html.HtmlElements
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.map
import lt.petuska.hazelcast.explorer.app.router
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.store.SelectionStore

internal fun HtmlElements.home() = div("container") {
  router.render { sel ->
    ul("nav nav-tabs nav-fill mb-3") {
      ExploreType.values().mapNotNull {
        val valid = when (it) {
          ExploreType.MAP -> !sel.target?.maps.isNullOrEmpty()
          ExploreType.TOPIC -> !sel.target?.topics.isNullOrEmpty()
        }
        if (valid) buildTab(it) else null
      }
    }
  }.bind()
  router.render {
    div("tab-content") {
    }
  }.bind()
}

private fun HtmlElements.buildTab(exploreType: ExploreType) = li("nav-item") {
  attr("role", "button")
  a("nav-link") {
    className = router.map { if (it.exploreType == exploreType) "active" else "" }
    clicks.map { exploreType } handledBy SelectionStore.selectExploreType
    +"${exploreType.displayName} Explorer"
  }
}
