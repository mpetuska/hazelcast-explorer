package lt.petuska.hazelcast.explorer.redux.reducer.environment

import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.manager.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*


object EnvironmentsReducer : HzeReducer<List<EnvironmentDTO>?>(HzeState::environments) {
  override fun reduce(state: List<EnvironmentDTO>?, action: HzeAction) = when (action) {
    is HzeAction.HzeConfigLoaded -> action.hzeConfig.environments
    else -> state
  }
}