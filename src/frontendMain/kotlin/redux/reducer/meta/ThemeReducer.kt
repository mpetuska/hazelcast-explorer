package lt.petuska.hazelcast.explorer.redux.reducer.meta

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*

object ThemeReducer : HzeReducer<Theme>(HzeState::theme) {
  override fun reduce(state: Theme, action: HzeAction) = when (action) {
    is HzeAction.SetTheme -> action.theme
    else -> state
  }
}