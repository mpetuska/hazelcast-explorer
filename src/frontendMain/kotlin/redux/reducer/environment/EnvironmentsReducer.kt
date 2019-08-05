package lt.petuska.hazelcast.explorer.redux.reducer.environment

import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.manager.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*
import lt.petuska.hazelcast.explorer.service.meta.*
import lt.petuska.hazelcast.explorer.service.util.*


object EnvironmentsReducer : HzeReducer<List<EnvironmentDTO>?>(HzeState::environments) {
  override fun reduce(state: List<EnvironmentDTO>?, action: HzeAction) = when (action) {
    is HzeAction.LoadHzeConfig -> state.also {
      HzeConfigService.get().then {
        store.dispatch(HzeAction.HzeConfigLoaded(it))
      }.catch {
        NotificationService.error("Unable to load the configuration from server")
      }
    }
    is HzeAction.HzeConfigLoaded -> action.hzeConfig.environments.also {
      LocalStorageManager
      HashQueryManager
    }
    else -> state
  }
}