package lt.petuska.hazelcast.explorer.redux.reducer.environment

import io.ktor.http.HttpMethod
import lt.petuska.hazelcast.explorer.domain.environment.EnvironmentDTO
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer
import lt.petuska.hazelcast.explorer.store
import lt.petuska.hazelcast.explorer.util.asyncDispatch

object SelectedEnvironmentReducer : HzeReducer<EnvironmentDTO?>(HzeState::selectedEnvironment) {
  override fun reduce(state: EnvironmentDTO?, action: HzeAction) = when (action) {
    is HzeAction.SelectEnvironment -> action.environment.also {
      if (action.environment?.readOnly == true) {
        store.asyncDispatch(HzeAction.SelectRestMethod(HttpMethod.Get))
      }
    }
    else -> state
  }
}