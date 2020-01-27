package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control.getComboButton

import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import react.RProps

interface GetMapButtonProps : RProps {
  var environment: Environment?
  var target: Target?
  var selectedMap: Map?
  var insertedKey: String?
  var type: BType
}