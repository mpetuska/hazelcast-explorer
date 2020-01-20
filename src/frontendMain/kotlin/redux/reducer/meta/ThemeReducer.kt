package lt.petuska.hazelcast.explorer.redux.reducer.meta

import lt.petuska.hazelcast.explorer.domain.enumerator.Theme
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer

object ThemeReducer : HzeReducer<Theme>(HzeState::theme) {
  override fun reduce(state: Theme, action: HzeAction) = when (action) {
    is HzeAction.SetTheme -> action.theme
    else -> state
  }
}