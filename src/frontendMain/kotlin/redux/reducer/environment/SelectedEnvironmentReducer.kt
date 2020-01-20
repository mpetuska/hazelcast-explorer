package lt.petuska.hazelcast.explorer.redux.reducer.environment

import lt.petuska.hazelcast.explorer.domain.environment.EnvironmentDTO
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer

object SelectedEnvironmentReducer : HzeReducer<EnvironmentDTO?>(HzeState::selectedEnvironment) {
  override fun reduce(state: EnvironmentDTO?, action: HzeAction) = when (action) {
    is HzeAction.SelectEnvironment -> action.environment
    else -> state
  }
}