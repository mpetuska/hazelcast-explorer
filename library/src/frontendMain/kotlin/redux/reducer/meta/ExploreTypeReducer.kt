package lt.petuska.hazelcast.explorer.redux.reducer.meta

import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import lt.petuska.hazelcast.explorer.redux.reducer.HzeReducer

object ExploreTypeReducer : HzeReducer<ExploreType?>(HzeState::selectedExploreType) {
  override fun reduce(state: ExploreType?, action: HzeAction) = when (action) {
    is HzeAction.SelectExploreType -> action.exploreType
    else -> state
  }
}