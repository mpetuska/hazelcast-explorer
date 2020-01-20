package lt.petuska.hazelcast.explorer.redux

import lt.petuska.hazelcast.explorer.redux.reducer.environment.EnvironmentsReducer
import lt.petuska.hazelcast.explorer.redux.reducer.environment.SelectedEnvironmentReducer
import lt.petuska.hazelcast.explorer.redux.reducer.environment.SelectedTargetReducer
import lt.petuska.hazelcast.explorer.redux.reducer.meta.AppLoadedReducer
import lt.petuska.hazelcast.explorer.redux.reducer.meta.ExploreTypeReducer
import lt.petuska.hazelcast.explorer.redux.reducer.meta.ThemeReducer
import lt.petuska.hazelcast.explorer.redux.reducer.meta.TitleReducer
import lt.petuska.hazelcast.explorer.redux.reducer.page.MapPageStateReducer
import lt.petuska.hazelcast.explorer.redux.reducer.page.TopicPageStateReducer
import lt.petuska.hazelcast.explorer.util.combineReducers
import redux.RAction
import redux.Reducer

object HzeReducers {
  operator fun invoke() = combineReducers(
    AppLoadedReducer,
    TitleReducer,
    ThemeReducer,
    ExploreTypeReducer,
    TopicPageStateReducer,
    EnvironmentsReducer,
    SelectedEnvironmentReducer,
    SelectedTargetReducer,
    MapPageStateReducer
  ).unsafeCast<Reducer<HzeState, RAction>>()
}