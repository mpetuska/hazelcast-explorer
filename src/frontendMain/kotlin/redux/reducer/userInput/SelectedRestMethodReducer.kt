package lt.petuska.hazelcast.explorer.redux.reducer.userInput

import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.redux.*
import lt.petuska.hazelcast.explorer.redux.reducer.*

object SelectedRestMethodReducer : HzeReducer<RestMethod>(HzeState::selectedRestMethod) {
  override fun reduce(state: RestMethod, action: HzeAction) = when (action) {
    is HzeAction.SelectRestMethod -> action.restMethod
    else -> state
  }
}