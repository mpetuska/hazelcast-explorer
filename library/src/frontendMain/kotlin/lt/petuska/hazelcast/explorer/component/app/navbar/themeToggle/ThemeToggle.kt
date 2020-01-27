package lt.petuska.hazelcast.explorer.component.app.navbar.themeToggle

import kotlinx.html.role
import lt.petuska.hazelcast.explorer.component.StatelessComponent
import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.store
import lt.petuska.hazelcast.explorer.types.BootstrapSwitchButton
import org.w3c.dom.HTMLLinkElement
import react.RBuilder
import react.dom.div
import react.setState
import kotlin.browser.document

class ThemeToggle(props: ThemeToggleProps) : StatelessComponent<ThemeToggleProps>(props) {
  override fun RBuilder.render() {
    div("navbar-text mr-4 mr-lg-2 cursor-pointer") {
      attrs.role = "button"
      Theme.values().forEach {
        val theme = document.getElementById("theme-${it.themeName}") as HTMLLinkElement?
        theme?.let { t ->
          if (it == props.theme) {
            t.removeAttribute("disabled")
          } else {
            t.setAttribute("disabled", "")
          }
        }
      }
      val isDark = props.theme == Theme.DARK
      child(BootstrapSwitchButton::class) {
        attrs {
          width = 60
          size = "xs"
          onstyle = BType.DARK.typeName
          onlabel = "Dark"
          offlabel = "Light"
          checked = isDark
          onChange = {
            setState {
              checked = it
            }
            store.dispatch(HzeAction.SetTheme(if (it) Theme.DARK else Theme.LIGHT))
          }
        }
      }
    }
  }
}