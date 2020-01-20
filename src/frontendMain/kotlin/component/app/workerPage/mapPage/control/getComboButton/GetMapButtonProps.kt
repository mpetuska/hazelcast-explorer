package component.app.workerPage.mapPage.control.getComboButton

import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.MapDTO
import react.RProps

interface GetMapButtonProps : RProps {
  var target: TargetDTO?
  var selectedMap: MapDTO?
  var insertedKey: String?
  var type: BType
}