package lt.petuska.hazelcast.explorer.redux.reducer.meta

import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*

object TitleReducer : HzeReducer<String>(HzeState::title) {
  override fun reduce(state: String, action: HzeAction) = when (action) {
    is HzeAction.HzeConfigLoaded -> action.hzeConfig.title
    is HzeAction.SetTitle -> action.title
    else -> state
  }
}