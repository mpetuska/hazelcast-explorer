package lt.petuska.hazelcast.explorer.redux.reducer.environment

import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer
import lt.petuska.hazelcast.explorer.store
import lt.petuska.hazelcast.explorer.util.asyncDispatch

object SelectedTargetReducer : HzeReducer<TargetDTO?>(HzeState::selectedTarget) {
  override fun reduce(state: TargetDTO?, action: HzeAction) = when (action) {
    is HzeAction.SelectTarget -> action.target.also {
      if (it != state) {
        store.asyncDispatch(HzeAction.ResetMapServerResponse)
      }
    }
    else -> state
  }
}