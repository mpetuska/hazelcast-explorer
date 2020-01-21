package lt.petuska.hazelcast.explorer.redux.reducer.meta

import lt.petuska.hazelcast.explorer.manager.HashQueryManager
import lt.petuska.hazelcast.explorer.manager.LocalStorageManager
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer

object AppLoadedReducer : HzeReducer<Boolean>(HzeState::loaded) {
  override fun reduce(state: Boolean, action: HzeAction) = when (action) {
    is HzeAction.HzeConfigLoaded -> true.also {
      LocalStorageManager
      HashQueryManager
    }
    else -> state
  }
}