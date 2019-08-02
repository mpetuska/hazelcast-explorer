package lt.petuska.hazelcast.explorer.util

import lt.petuska.hazelcast.explorer.redux.*
import react.*
import react.redux.*
import redux.*
import kotlin.reflect.*
import redux.combineReducers as combineReducersOriginal

fun <S, A> combineReducers(reducers: Map<KProperty1<S, *>, Reducer<*, A>>): Reducer<S, A> {
  return combineReducersOriginal(reducers.mapKeys { it.key.name })
}

fun <S, A> combineReducers(vararg reducers: Pair<KProperty1<S, *>, Reducer<*, A>>): Reducer<S, A> {
  return combineReducers(reducers.toMap())
}

inline fun <CP : RProps, SP : RProps, DP : RProps, reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (SP.(HZEState, CP) -> Unit) = { _, _ -> },
  noinline mapDispatchToProps: (DP.((HZEAction) -> WrapperAction, CP) -> Unit) = { _, _ -> },
  noinline options: (Options<HZEState, CP, SP, P>.() -> Unit) = {}
): RClass<CP> {
  return rConnect<HZEState, HZEAction, WrapperAction, CP, SP, DP, P>(
    mapStateToProps,
    mapDispatchToProps,
    options
  )(T::class.rClass)
}

inline fun <CP : RProps, SP : RProps, reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (SP.(HZEState, CP) -> Unit) = { _, _ -> },
  noinline options: (Options<HZEState, CP, SP, P>.() -> Unit) = {}
): RClass<CP> {
  return connectClass<CP, SP, RProps, T, P>(
    mapStateToProps,
    { _, _ -> },
    options
  )
}

inline fun <reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (P.(HZEState, P) -> Unit) = { _, _ -> },
  noinline options: (Options<HZEState, P, P, P>.() -> Unit) = {}
): RClass<P> {
  return connectClass<P, P, RProps, T, P>(
    mapStateToProps,
    { _, _ -> },
    options
  )
}

inline fun <SP : RProps, DP : RProps, reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (SP.(HZEState) -> Unit) = { _ -> },
  noinline mapDispatchToProps: (DP.((HZEAction) -> WrapperAction) -> Unit) = { _ -> },
  noinline options: (Options<HZEState, RProps, SP, P>.() -> Unit) = {}
): RClass<RProps> {
  val mstp: SP.(HZEState, RProps) -> Unit = { s, _ ->
    mapStateToProps.invoke(this, s)
  }
  val mdtp: DP.((HZEAction) -> WrapperAction, RProps) -> Unit = { dispatch, _ ->
    mapDispatchToProps.invoke(this, dispatch)
  }
  return connectClass<RProps, SP, DP, T, P>(mstp, mdtp, options)
}

inline fun <SP : RProps, reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (SP.(HZEState) -> Unit) = { _ -> },
  noinline options: (Options<HZEState, RProps, SP, P>.() -> Unit) = {}
): RClass<RProps> {
  val mstp: SP.(HZEState) -> Unit = { s ->
    mapStateToProps.invoke(this, s)
  }
  val mdtp: RProps.((HZEAction) -> WrapperAction) -> Unit = { _ ->
  }
  return connectClass<SP, RProps, T, P>(mstp, mdtp, options)
}

inline fun <reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (P.(HZEState) -> Unit) = { _ -> },
  noinline options: (Options<HZEState, RProps, P, P>.() -> Unit) = {}
): RClass<RProps> {
  return connectClass<P, T, P>(mapStateToProps, options)
}

inline fun <reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline options: (Options<HZEState, RProps, RProps, P>.() -> Unit) = {}
): RClass<RProps> {
  val mstp: RProps.(HZEState) -> Unit = { _ ->
  }
  return connectClass<RProps, T, P>(mstp, options)
}