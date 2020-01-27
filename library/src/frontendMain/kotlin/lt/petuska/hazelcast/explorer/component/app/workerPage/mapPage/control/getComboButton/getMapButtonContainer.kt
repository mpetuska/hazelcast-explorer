package lt.petuska.hazelcast.explorer.component.app.workerPage.mapPage.control.getComboButton

import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.util.connectClass

val getMapButton = connectClass<GetMapButton, GetMapButtonProps>(
  mapStateToProps = { state ->
    environment = state.environment
    target = state.target
    selectedMap = state.map
    insertedKey = state.mapKey
    type = BType.SUCCESS
  }
)