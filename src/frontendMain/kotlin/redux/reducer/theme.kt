package lt.petuska.hazelcast.explorer.redux.reducer

import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.redux.*
import redux.*

fun theme(state: Theme = Theme.DARK, action: RAction) = when (action) {
  is HZEAction.SetTheme -> action.theme
  else -> state
}