package lt.petuska.hazelcast.explorer.redux

import lt.petuska.hazelcast.explorer.redux.reducer.environment.*
import lt.petuska.hazelcast.explorer.redux.reducer.meta.*
import lt.petuska.hazelcast.explorer.redux.reducer.userInput.*
import lt.petuska.hazelcast.explorer.util.*
import redux.*

object HzeReducers {
  operator fun invoke() = combineReducers(
      TitleReducer,
      ThemeReducer,
      ExploreTypeReducer,
      SelectedRestMethodReducer,
      SelectedMapReducer,
      SelectedTopicReducer,
      EnvironmentsReducer,
      SelectedEnvironmentReducer,
      SelectedTargetReducer
  ).unsafeCast<Reducer<HzeState, RAction>>()
}