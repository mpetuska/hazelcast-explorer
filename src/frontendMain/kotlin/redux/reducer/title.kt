package lt.petuska.hazelcast.explorer.redux.reducer

import lt.petuska.hazelcast.explorer.redux.*
import redux.*

fun title(state: String = "", action: RAction) = when (action) {
  is HZEAction.HZEConfigLoaded -> action.hzeConfig.title
  is HZEAction.SetTitle -> action.title
  else -> state
}