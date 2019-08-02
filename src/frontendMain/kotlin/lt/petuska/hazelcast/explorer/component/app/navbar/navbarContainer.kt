package lt.petuska.hazelcast.explorer.component.app.navbar

import lt.petuska.hazelcast.explorer.util.*

val navbar = connectClass<Navbar, NavbarProps>(
  mapStateToProps = { state ->
    title = state.title
    theme = state.theme
    environments = state.environments
    selectedEnvironment = state.selectedEnvironment
    selectedTarget = state.selectedTarget
    selectedExploreType = state.selectedExploreType
  }
)