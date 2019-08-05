package component.app.workerPage.mapPage.control.getComboButton

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.util.*

val getMapButton = connectClass<GetMapButton, GetMapButtonProps>(
    mapStateToProps = { state ->
      selectedMap = state.mapMageState.selectedMap
      insertedKey = state.mapMageState.insertedKey
      type = BType.SUCCESS
    }
)