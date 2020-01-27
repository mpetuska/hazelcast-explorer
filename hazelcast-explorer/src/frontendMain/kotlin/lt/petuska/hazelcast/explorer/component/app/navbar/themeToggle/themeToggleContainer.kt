package lt.petuska.hazelcast.explorer.component.app.navbar.themeToggle

import lt.petuska.hazelcast.explorer.util.connectClass

val themeToggle = connectClass<ThemeToggle, ThemeToggleProps>(
  mapStateToProps = { state ->
    theme = state.theme
  }
)