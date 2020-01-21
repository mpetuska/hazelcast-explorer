package lt.petuska.hazelcast.explorer.redux.reducer.environment

import lt.petuska.hazelcast.explorer.domain.environment.EnvironmentDTO
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer


object EnvironmentsReducer : HzeReducer<List<EnvironmentDTO>?>(HzeState::environments) {
  override fun reduce(state: List<EnvironmentDTO>?, action: HzeAction) = when (action) {
    is HzeAction.HzeConfigLoaded -> action.hzeConfig.environments
    else -> state
  }
}