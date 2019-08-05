package component.app.workerPage.mapPage.control

import io.ktor.http.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import react.*

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