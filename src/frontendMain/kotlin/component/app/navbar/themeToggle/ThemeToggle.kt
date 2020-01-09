package lt.petuska.hazelcast.explorer.component.app.navbar.themeToggle

import kotlinx.html.*
import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.component.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.types.*
import lt.petuska.hazelcast.explorer.util.*
import org.w3c.dom.*
import react.*
import react.dom.*
import kotlin.browser.*

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
      child(BootstrapSwitchButton::class){
        attrs {
          //          key = "theme-toggle-${props.theme}"
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