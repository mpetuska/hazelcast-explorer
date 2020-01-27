package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import lt.petuska.hazelcast.explorer.util.connectClass

val mapPage = connectClass<MapPage, MapPageProps>(
  mapStateToProps = { state ->
    theme = state.theme
    serverResponseStatus = state.mapResponseStatus
    serverResponseJson = state.mapResponseJson
  }
)