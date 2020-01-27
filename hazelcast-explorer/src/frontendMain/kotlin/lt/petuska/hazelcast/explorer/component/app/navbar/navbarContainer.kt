package lt.petuska.hazelcast.explorer.component.app.navbar

import lt.petuska.hazelcast.explorer.util.connectClass

val navbar = connectClass<Navbar, NavbarProps>(
  mapStateToProps = { state ->
    theme = state.theme
    selectedEnvironment = state.environment
    selectedTarget = state.target
    selectedExploreType = state.exploreType
    selectedMethod = state.mapMethod
  }
)