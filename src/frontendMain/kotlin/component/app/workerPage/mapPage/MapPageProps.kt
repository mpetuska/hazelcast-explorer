package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import kotlinext.js.JsObject
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.MapDTO
import react.RProps

interface MapPageProps : RProps {
  var theme: Theme
  var target: TargetDTO?
  var selectedHttpMethod: HttpMethod
  var selectedMap: MapDTO?
  var insertedKey: String?
  var insertedJson: String?
  var currentRequestTimestamp: Long?
  var serverResponseStatus: HttpStatusCode?
  var serverResponseJson: JsObject?
}