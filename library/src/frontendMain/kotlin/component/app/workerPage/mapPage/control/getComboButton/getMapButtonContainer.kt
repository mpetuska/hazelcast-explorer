package component.app.workerPage.mapPage.control.getComboButton

import lt.petuska.hazelcast.explorer.domain.enumerator.BType
import lt.petuska.hazelcast.explorer.util.connectClass

val getMapButton = connectClass<GetMapButton, GetMapButtonProps>(
  mapStateToProps = { state ->
    target = state.selectedTarget
    selectedMap = state.mapMageState.selectedMap
    insertedKey = state.mapMageState.insertedKey
    type = BType.SUCCESS
  }
)