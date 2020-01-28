package lt.petuska.hazelcast.explorer.component.app.navbar

import kotlinx.css.Display
import kotlinx.css.VerticalAlign
import kotlinx.css.display
import kotlinx.css.verticalAlign
import kotlinx.html.classes
import kotlinx.html.id
import kotlinx.html.role
import lt.petuska.hazelcast.explorer.component.StatelessComponent
import lt.petuska.hazelcast.explorer.component.app.navbar.logo.logo
import lt.petuska.hazelcast.explorer.component.app.navbar.themeToggle.themeToggle
import lt.petuska.hazelcast.explorer.config
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.domain.enumerator.PersistentProperty
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.manager.HashQueryManager
import react.RBuilder
import react.dom.a
import react.dom.b
import react.dom.button
import react.dom.div
import react.dom.nav
import react.dom.span
import styled.css
import styled.styledH4
import kotlin.browser.document

class Navbar(appProps: NavbarProps) : StatelessComponent<NavbarProps>(appProps) {
  override fun RBuilder.render() {
    document.getElementById("title")?.firstChild?.nodeValue = config.title
    nav("navbar navbar-expand-lg navbar-dark ${if (props.theme == Theme.DARK) "bg-dark" else "bg-primary"} p-2 mb-3") {
      brand()
      navigationLinks()
      div("d-flex flex-row align-items-center") {
        statusIndicators()
        themeToggle {}
        navbarToggler()
      }
    }
  }
  
  private fun RBuilder.statusIndicators() = div("navbar-text mr-4 d-flex flex-column justify-content-between p-0") {
    props.selectedEnvironment?.let {
      span("d-flex flex-row justify-content-between align-items-center") {
        span("mr-2") { +"Selected Environment:" }
        span("badge badge-pill badge-${if (it.readOnly) BType.WARNING else BType.INFO}") { +it.displayName }
      }
    }
    props.selectedTarget?.let {
      span("d-flex flex-row justify-content-between align-items-center") {
        span("mr-2") { +"Selected Target:" }
        span("badge badge-pill badge-${if (it.readOnly) BType.WARNING else BType.INFO}") { +it.displayName }
      }
    }
  }
  
  private fun RBuilder.navigationLinks() = div("collapse navbar-collapse ml-3 p-1") {
    attrs.id = "navbarText"
    div("navbar-nav mr-auto") {
      config.environments.forEach { env ->
        div("nav-item dropdown") {
          div(classes = "nav-link dropdown-toggle cursor-pointer") {
            attrs {
              id = "nav-dropdown-${env.name}"
              role = "button"
              set("data-toggle", "dropdown")
              set("aria-haspopup", "true")
              set("aria-expanded", "false")
            }
            +env.displayName
          }
          div("dropdown-menu") {
            attrs {
              set("aria-labelledby", "nav-dropdown-${env.name}")
            }
            env.targets.forEach { target ->
              a(
                HashQueryManager.buildHashLink(
                  PersistentProperty.ENVIRONMENT to env.name,
                  PersistentProperty.TARGET to target.name
                ), classes = "dropdown-item"
              ) {
                +target.displayName
              }
            }
          }
        }
      }
    }
  }
  
  
  private fun RBuilder.navbarToggler() = button {
    attrs {
      classes = setOf("navbar-toggler")
      set("data-toggle", "collapse")
      set("data-target", "#navbarText")
      set("aria-controls", "navbarText")
      set("aria-expanded", "false")
      set("aria-label", "Toggle navigation")
    }
    span("navbar-toggler-icon") { }
  }
  
  private fun RBuilder.brand() = a("?#?", "navbar-brand nav-link") {
    logo {}
    styledH4 {
      css {
        display = Display.inline
        verticalAlign = VerticalAlign.middle
      }
      b { +config.title }
    }
  }
}