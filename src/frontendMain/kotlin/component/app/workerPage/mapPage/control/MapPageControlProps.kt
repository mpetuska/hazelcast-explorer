package component.app.workerPage.mapPage.control

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.MapDTO
import react.RProps

interface MapPageControlProps : RProps {
  var target: TargetDTO?
  var theme: Theme
  var selectedHttpMethod: HttpMethod
  var selectedMap: MapDTO?
  var insertedKey: String?
  var insertedJson: String?
  var currentRequestTimestamp: Long?
  var serverResponseStatus: HttpStatusCode?
}