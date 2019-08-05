package component.app.workerPage.mapPage.control.getComboButton

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import react.*

interface GetMapButtonProps : RProps {
  var selectedMap: MapDTO?
  var insertedKey: String?
  var type: BType
}