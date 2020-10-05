package lt.petuska.hazelcast.explorer.app

import dev.fritz2.dom.html.HtmlElements
import dev.fritz2.dom.html.render
import dev.fritz2.routing.router
import lt.petuska.hazelcast.explorer.app.component.navBar
import lt.petuska.hazelcast.explorer.app.page.home
import lt.petuska.hazelcast.explorer.store.Route

internal val router by lazy {
  router(Route())
}

internal fun HtmlElements.app() = div {
  navBar()
  router.render { route ->
    when {
      route.environment == null -> {
        h4("text-center") { +"Please select an environment & target to explore" }
      }
      route.target == null -> {
        h4("text-center") { +"Please select a target to explore" }
      }
      else -> home()
    }
  }.bind()
}
