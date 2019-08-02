package lt.petuska.hazelcast.explorer.redux.reducer

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*
import redux.*

fun selectedExploreType(state: ExploreType? = null, action: RAction) = when (action) {
  is HZEAction.SelectExploreType -> action.exploreType
  else -> state
}