package lt.petuska.hazelcast.explorer.app.component

import dev.fritz2.binding.handledBy
import dev.fritz2.dom.html.HtmlElements
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.map
import lt.petuska.hazelcast.explorer.app.router
import lt.petuska.hazelcast.explorer.config
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.store.SelectionStore
import lt.petuska.hazelcast.explorer.store.isDarkTheme

internal fun HtmlElements.navBar() = nav("navbar navbar-expand-lg navbar-dark p-2 mb-3") {
  className = isDarkTheme.data.map { if (it) "bg-dark" else "bg-primary" }
  brand()
  navigationLinks()
  div("d-flex flex-row align-items-center") {
    statusIndicators()
    themeToggle()
    navbarToggle()
  }
}

private fun HtmlElements.statusIndicators() = div("navbar-text mr-4 d-flex flex-column justify-content-between p-0") {
  router.render { sel ->
    div {
      sel.environment?.let {
        span("d-flex flex-row justify-content-between align-items-center") {
          span("mr-2") { +"Selected Environment:" }
          span("badge badge badge-${if (it.readOnly) BType.WARNING else BType.INFO}") { +it.displayName }
        }
      }
      sel.target?.let {
        span("d-flex flex-row justify-content-between align-items-center") {
          span("mr-2") { +"Selected Target:" }
          span("badge badge badge-${if (it.readOnly) BType.WARNING else BType.INFO}") { +it.displayName }
        }
      }
    }
  }.bind()
}

private fun HtmlElements.themeToggle() = i("navbar-text mr-4 mr-lg-2 cursor-pointer fas fa-lg") {
  className = isDarkTheme.data.map { if (it) "fa-sun" else "fa-moon" }
  clicks handledBy isDarkTheme.handle { !it }
}

private fun HtmlElements.navbarToggle() = button("navbar-toggler") {
  attr("data-toggle", "collapse")
  attr("data-target", "#navbarText")
  attr("aria-controls", "navbarText")
  attr("aria-expanded", "false")
  attr("aria-label", "Toggle navigation")
  span("navbar-toggler-icon") {}
}

private fun HtmlElements.brand() = a("navbar-brand nav-link") {
  attr("role", "button")
  h4("row align-items-center font-weight-bold p-0 m-0") {
    logo()
    +config.title
  }
  clicks handledBy SelectionStore.clearSelections
}

private fun HtmlElements.navigationLinks() = div("collapse navbar-collapse ml-3 p-1", "navbarText") {
  div("navbar-nav mr-auto") {
    config.environments.forEach { env ->
      div("nav-item dropdown") {
        div("nav-link dropdown-toggle cursor-pointer", "nav-dropdown-${env.name}") {
          attr("role", "button")
          attr("data-toggle", "dropdown")
          attr("aria-haspopup", "true")
          attr("aria-expanded", "false")
          +env.displayName
        }
        div("dropdown-menu") {
          attr("aria-labelledby", "nav-dropdown-${env.name}")
          env.targets.forEach { target ->
            a(
              "dropdown-item"
            ) {
              className = router.map {
                if (it.environment == env && it.target == target) "active" else ""
              }
              attr("role", "button")
              +target.displayName
              clicks.map { env.name to target.name } handledBy SelectionStore.selectTarget
            }
          }
        }
      }
    }
  }
}
