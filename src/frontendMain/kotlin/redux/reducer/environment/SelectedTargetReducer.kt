package lt.petuska.hazelcast.explorer.redux.reducer.environment

import lt.petuska.hazelcast.explorer.domain.environment.target.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*

object SelectedTargetReducer : HzeReducer<TargetDTO?>(HzeState::selectedTarget) {
  override fun reduce(state: TargetDTO?, action: HzeAction) = when (action) {
    is HzeAction.SelectTarget -> action.target
    else -> state
  }
}