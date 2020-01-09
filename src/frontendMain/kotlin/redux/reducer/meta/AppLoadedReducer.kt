package lt.petuska.hazelcast.explorer.redux.reducer.meta

import lt.petuska.hazelcast.explorer.manager.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*

object AppLoadedReducer : HzeReducer<Boolean>(HzeState::loaded) {
  override fun reduce(state: Boolean, action: HzeAction) = when (action) {
    is HzeAction.HzeConfigLoaded -> true.also {
      LocalStorageManager
      HashQueryManager
    }
    else -> state
  }
}