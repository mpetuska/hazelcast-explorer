package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import io.ktor.http.HttpStatusCode
import kotlinext.js.JsObject
import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import react.RProps

interface MapPageProps : RProps {
  var theme: Theme
  var serverResponseStatus: HttpStatusCode?
  var serverResponseJson: JsObject?
}