package lt.petuska.hazelcast.explorer.component.app

import lt.petuska.hazelcast.explorer.util.connectClass


val app = connectClass<App, AppProps>(
  mapStateToProps = { state ->
    selectedEnvironment = state.environment
    selectTarget = state.target
  }
)