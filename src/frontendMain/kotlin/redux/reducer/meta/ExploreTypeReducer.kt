package lt.petuska.hazelcast.explorer.redux.reducer.meta

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*

object ExploreTypeReducer : HzeReducer<ExploreType?>(HzeState::selectedExploreType) {
  override fun reduce(state: ExploreType?, action: HzeAction) = when (action) {
    is HzeAction.SelectExploreType -> action.exploreType
    else -> state
  }
}