package lt.petuska.hazelcast.explorer.redux

import lt.petuska.hazelcast.explorer.redux.reducer.*
import lt.petuska.hazelcast.explorer.util.*
import redux.*

fun combinedReducers(): Reducer<HZEState, RAction> = combineReducers(
  HZEState::title to ::title,
  HZEState::theme to ::theme,
  HZEState::environments to ::environments,
  HZEState::selectedEnvironment to ::selectedEnvironment,
  HZEState::selectedTarget to ::selectedTarget,
  HZEState::selectedExploreType to ::selectedExploreType
)