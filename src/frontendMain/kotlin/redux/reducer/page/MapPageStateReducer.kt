package lt.petuska.hazelcast.explorer.redux.reducer.page

import kotlinext.js.JsObject
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer
import lt.petuska.hazelcast.explorer.redux.state.MapPageState
import lt.petuska.hazelcast.explorer.store
import lt.petuska.hazelcast.explorer.util.asyncDispatch


object MapPageStateReducer : HzeReducer<MapPageState>(HzeState::mapMageState) {
  override fun reduce(state: MapPageState, action: HzeAction) = when (action) {
    is HzeAction.ResetMapServerResponse -> state.copy(serverResponseJson = null, serverResponseStatus = null)
    is HzeAction.SelectRestMethod -> state.copy(selectedRestMethod = action.restMethod).also {
      if (state.selectedRestMethod != action.restMethod)
        store.asyncDispatch(HzeAction.ResetMapServerResponse)
    }
    is HzeAction.SelectMap -> state.copy(selectedMap = action.map).also {
      if (state.selectedMap != action.map)
        store.asyncDispatch(HzeAction.ResetMapServerResponse)
    }
    is HzeAction.SetMapInsertedKey -> state.copy(insertedKey = action.insertedKey?.takeIf { it.isNotBlank() })
    is HzeAction.SetMapInsertedJson -> state.copy(insertedJson = action.insertedJson?.takeIf { it.isNotBlank() })
    is HzeAction.SetMapCurrentRequestTimestamp -> state.copy(currentRequestTimestamp = action.timestamp)
    is HzeAction.SetMapServerResponseStatus -> state.copy(serverResponseStatus = action.status)
    is HzeAction.SetMapServerResponseJson -> state.copy(serverResponseJson = action.json?.let { JSON.parse<JsObject>(it) })
    else -> state
  }
}