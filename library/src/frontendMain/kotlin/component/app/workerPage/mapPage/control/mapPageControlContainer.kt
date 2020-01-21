package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control

import component.app.workerPage.mapPage.control.MapPageControl
import component.app.workerPage.mapPage.control.MapPageControlProps
import io.ktor.http.HttpMethod
import lt.petuska.hazelcast.explorer.util.connectClass

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
    httpMethods =
      if (state.selectedEnvironment?.readOnly != true && state.selectedTarget?.readOnly != true && selectedMap?.readOnly != true) {
        setOf(HttpMethod.Get.value, HttpMethod.Post.value, HttpMethod.Put.value, HttpMethod.Delete.value)
      } else {
        setOf(HttpMethod.Get.value)
      }
  }
)