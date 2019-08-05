package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control

import component.app.workerPage.mapPage.control.*
import lt.petuska.hazelcast.explorer.util.*

val mapPageControl = connectClass<MapPageControl, MapPageControlProps>(
    mapStateToProps = { state ->
      target = state.selectedTarget
      theme = state.theme
      selectedHttpMethod = state.mapMageState.selectedRestMethod
      selectedMap = state.mapMageState.selectedMap
      insertedKey = state.mapMageState.insertedKey
      insertedJson = state.mapMageState.insertedJson
      currentRequestTimestamp = state.mapMageState.currentRequestTimestamp
      serverResponseStatus = state.mapMageState.serverResponseStatus
    }
)