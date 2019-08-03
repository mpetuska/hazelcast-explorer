package lt.petuska.hazelcast.explorer.redux.reducer.environment

import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*

object SelectedEnvironmentReducer : HzeReducer<EnvironmentDTO?>(HzeState::selectedEnvironment) {
  override fun reduce(state: EnvironmentDTO?, action: HzeAction) = when (action) {
    is HzeAction.SelectEnvironment -> action.environment
    else -> state
  }
}