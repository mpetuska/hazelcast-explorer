package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import react.RProps

interface MapPageControlProps : RProps {
  var environment: Environment?
  var target: Target?
  var theme: Theme
  var selectedHttpMethod: HttpMethod
  var selectedMap: Map?
  var insertedKey: String?
  var insertedJson: String?
  var serverResponseStatus: HttpStatusCode?
  var httpMethods: Set<String>
}