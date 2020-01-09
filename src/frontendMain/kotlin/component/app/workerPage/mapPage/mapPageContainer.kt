package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import lt.petuska.hazelcast.explorer.util.*

val mapPage = connectClass<MapPage, MapPageProps>(
    mapStateToProps = { state ->
      theme = state.theme
      target = state.selectedTarget
      selectedHttpMethod = state.mapMageState.selectedRestMethod
      selectedMap = state.mapMageState.selectedMap
      insertedKey = state.mapMageState.insertedKey
      insertedJson = state.mapMageState.insertedJson
      currentRequestTimestamp = state.mapMageState.currentRequestTimestamp
      serverResponseStatus = state.mapMageState.serverResponseStatus
      serverResponseJson = state.mapMageState.serverResponseJson
    }
)