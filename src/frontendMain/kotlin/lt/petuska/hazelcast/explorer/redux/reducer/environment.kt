package lt.petuska.hazelcast.explorer.redux.reducer

import lt.petuska.hazelcast.explorer.domain.environment.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*
import lt.petuska.hazelcast.explorer.redux.*
import redux.*

fun environments(state: List<EnvironmentDTO> = listOf(), action: RAction) = when (action) {
  is HZEAction.HZEConfigLoaded -> action.hzeConfig.environments
  else -> state
}

fun selectedEnvironment(state: EnvironmentDTO? = null, action: RAction) = when (action) {
  is HZEAction.SelectEnvironment -> action.environment
  else -> state
}

fun selectedTarget(state: TargetDTO? = null, action: RAction) = when (action) {
  is HZEAction.SelectTarget -> {
    action.target
  }
  else -> state
}