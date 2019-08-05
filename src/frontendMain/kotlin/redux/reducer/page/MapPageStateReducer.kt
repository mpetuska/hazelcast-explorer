package lt.petuska.hazelcast.explorer.redux.reducer.page

import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*
import lt.petuska.hazelcast.explorer.redux.state.*


object MapPageStateReducer : HzeReducer<MapPageState>(HzeState::mapMageState) {
  override fun reduce(state: MapPageState, action: HzeAction) = when (action) {
    is HzeAction.SelectRestMethod -> state.copy(selectedRestMethod = action.restMethod)
    is HzeAction.SelectMap -> state.copy(selectedMap = action.map)
    is HzeAction.SetMapInsertedKey -> state.copy(insertedKey = action.insertedKey?.takeIf { it.isNotBlank() })
    is HzeAction.SetMapInsertedJson -> state.copy(insertedJson = action.insertedJson?.takeIf { it.isNotBlank() })
    is HzeAction.SetMapCurrentRequestTimestamp -> state.copy(currentRequestTimestamp = action.timestamp)
    is HzeAction.SetMapServerResponseStatus -> state.copy(serverResponseStatus = action.status)
    else -> state
  }
}