package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control

import io.ktor.http.HttpMethod
import lt.petuska.hazelcast.explorer.util.connectClass

val mapPageControl =
  connectClass<MapPageControl, MapPageControlProps>(
    mapStateToProps = { state ->
      environment = state.environment
      target = state.target
      theme = state.theme
      selectedHttpMethod = state.mapMethod
      selectedMap = state.map
      insertedKey = state.mapKey
      insertedJson = state.mapJson
      serverResponseStatus = state.mapResponseStatus
      httpMethods = with(state) {
        if (!state.map.readOnly) {
          setOf(HttpMethod.Get.value, HttpMethod.Post.value, HttpMethod.Put.value, HttpMethod.Delete.value)
        } else {
          setOf(HttpMethod.Get.value)
        }
      }
    }
  )