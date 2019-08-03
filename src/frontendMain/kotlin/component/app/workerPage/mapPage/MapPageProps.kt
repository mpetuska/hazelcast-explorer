package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import react.*

interface MapPageProps : RProps {
  var target: TargetDTO?
  var selectedRestMethod: RestMethod
  var selectedMap: MapDTO?
}