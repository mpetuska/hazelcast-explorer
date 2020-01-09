package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import io.ktor.http.*
import kotlinext.js.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import react.*

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