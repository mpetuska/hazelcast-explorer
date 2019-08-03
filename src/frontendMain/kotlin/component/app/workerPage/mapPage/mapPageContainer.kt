package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import lt.petuska.hazelcast.explorer.util.*

val mapPage = connectClass<MapPage, MapPageProps>(
    mapStateToProps = { state ->
      target = state.selectedTarget
      selectedRestMethod = state.selectedRestMethod
      selectedMap = state.selectedMap
    }
)