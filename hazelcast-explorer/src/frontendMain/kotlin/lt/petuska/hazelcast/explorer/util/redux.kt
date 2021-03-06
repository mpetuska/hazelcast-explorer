package lt.petuska.hazelcast.explorer.util

import lt.petuska.hazelcast.explorer.redux.HzeAction
import lt.petuska.hazelcast.explorer.redux.HzeState
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import react.invoke
import react.rClass
import react.redux.Options
import react.redux.rConnect
import redux.WrapperAction


inline fun <CP : RProps, SP : RProps, DP : RProps, reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (SP.(HzeState, CP) -> Unit) = { _, _ -> },
  noinline mapDispatchToProps: (DP.((HzeAction) -> WrapperAction, CP) -> Unit) = { _, _ -> },
  noinline options: (Options<HzeState, CP, SP, P>.() -> Unit) = {}
): RClass<CP> {
  return rConnect<HzeState, HzeAction, WrapperAction, CP, SP, DP, P>(
    mapStateToProps,
    mapDispatchToProps,
    options
  )(T::class.rClass)
}

inline fun <CP : RProps, SP : RProps, reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (SP.(HzeState, CP) -> Unit) = { _, _ -> },
  noinline options: (Options<HzeState, CP, SP, P>.() -> Unit) = {}
): RClass<CP> {
  return connectClass<CP, SP, RProps, T, P>(
    mapStateToProps,
    { _, _ -> },
    options
  )
}

inline fun <reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (P.(HzeState, P) -> Unit) = { _, _ -> },
  noinline options: (Options<HzeState, P, P, P>.() -> Unit) = {}
): RClass<P> {
  return connectClass<P, P, RProps, T, P>(
    mapStateToProps,
    { _, _ -> },
    options
  )
}

inline fun <SP : RProps, DP : RProps, reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (SP.(HzeState) -> Unit) = { _ -> },
  noinline mapDispatchToProps: (DP.((HzeAction) -> WrapperAction) -> Unit) = { _ -> },
  noinline options: (Options<HzeState, RProps, SP, P>.() -> Unit) = {}
): RClass<RProps> {
  val mstp: SP.(HzeState, RProps) -> Unit = { s, _ ->
    mapStateToProps.invoke(this, s)
  }
  val mdtp: DP.((HzeAction) -> WrapperAction, RProps) -> Unit = { dispatch, _ ->
    mapDispatchToProps.invoke(this, dispatch)
  }
  return connectClass<RProps, SP, DP, T, P>(mstp, mdtp, options)
}

inline fun <SP : RProps, reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (SP.(HzeState) -> Unit) = { _ -> },
  noinline options: (Options<HzeState, RProps, SP, P>.() -> Unit) = {}
): RClass<RProps> {
  val mstp: SP.(HzeState) -> Unit = { s ->
    mapStateToProps.invoke(this, s)
  }
  val mdtp: RProps.((HzeAction) -> WrapperAction) -> Unit = { _ ->
  }
  return connectClass<SP, RProps, T, P>(mstp, mdtp, options)
}

inline fun <reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline mapStateToProps: (P.(HzeState) -> Unit) = { _ -> },
  noinline options: (Options<HzeState, RProps, P, P>.() -> Unit) = {}
): RClass<RProps> {
  return connectClass<P, T, P>(mapStateToProps, options)
}

inline fun <reified T : RComponent<P, out RState>, P : RProps> connectClass(
  noinline options: (Options<HzeState, RProps, RProps, P>.() -> Unit) = {}
): RClass<RProps> {
  val mstp: RProps.(HzeState) -> Unit = { _ ->
  }
  return connectClass<RProps, T, P>(mstp, options)
}