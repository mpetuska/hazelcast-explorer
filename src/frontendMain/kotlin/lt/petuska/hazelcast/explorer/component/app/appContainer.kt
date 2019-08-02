package lt.petuska.hazelcast.explorer.component.app

import lt.petuska.hazelcast.explorer.util.*


val app = connectClass<App, AppProps>(
  mapStateToProps = { state ->
    title = state.title
    theme = state.theme
    selectedEnvironment = state.selectedEnvironment
    selectTarget = state.selectedTarget
  }
)