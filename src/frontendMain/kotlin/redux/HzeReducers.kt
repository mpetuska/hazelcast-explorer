package lt.petuska.hazelcast.explorer.redux

import lt.petuska.hazelcast.explorer.redux.reducer.environment.*
import lt.petuska.hazelcast.explorer.redux.reducer.meta.*
import lt.petuska.hazelcast.explorer.redux.reducer.page.*
import lt.petuska.hazelcast.explorer.redux.reducer.userInput.*
import lt.petuska.hazelcast.explorer.util.*
import redux.*

object HzeReducers {
  operator fun invoke() = combineReducers(
      TitleReducer,
      ThemeReducer,
      ExploreTypeReducer,
      SelectedTopicReducer,
      EnvironmentsReducer,
      SelectedEnvironmentReducer,
      SelectedTargetReducer,
      MapPageStateReducer
  ).unsafeCast<Reducer<HzeState, RAction>>()
}