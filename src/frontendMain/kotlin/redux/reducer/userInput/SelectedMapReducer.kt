package lt.petuska.hazelcast.explorer.redux.reducer.userInput

import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*

object SelectedMapReducer : HzeReducer<MapDTO?>(HzeState::selectedMap) {
  override fun reduce(state: MapDTO?, action: HzeAction) = when (action) {
    is HzeAction.SelectMap -> action.map
    else -> state
  }
}