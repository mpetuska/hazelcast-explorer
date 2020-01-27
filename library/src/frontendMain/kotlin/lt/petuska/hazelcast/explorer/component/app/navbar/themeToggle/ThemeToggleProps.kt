package lt.petuska.hazelcast.explorer.component.app.navbar.themeToggle

import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import react.RProps

interface ThemeToggleProps : RProps {
  var theme: Theme
}