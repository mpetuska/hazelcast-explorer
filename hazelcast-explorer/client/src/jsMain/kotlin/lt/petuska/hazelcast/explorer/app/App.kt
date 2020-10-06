package lt.petuska.hazelcast.explorer.app

import dev.fritz2.dom.html.HtmlElements
import dev.fritz2.routing.router
import kotlinx.coroutines.flow.map
import lt.petuska.hazelcast.explorer.app.component.navBar
import lt.petuska.hazelcast.explorer.app.page.home
import lt.petuska.hazelcast.explorer.store.Route

internal val router by lazy {
  router(Route())
}

internal fun HtmlElements.app() = div {
  navBar()
  h4("text-center") {
    router.map { it.environment != null }.bindAttr("hidden")
    +"Please select an environment & target to explore"
  }
  h4("text-center") {
    router.map { it.target != null }.bindAttr("hidden")
    +"Please select a target to explore"
  }
  home(router.map { it.environment == null && it.target == null })
}
