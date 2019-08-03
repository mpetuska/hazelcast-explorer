package lt.petuska.hazelcast.explorer.redux.reducer

import lt.petuska.hazelcast.explorer.redux.*
import kotlin.reflect.*

@Suppress("LeakingThis")
abstract class HzeReducer<T>(private val prop: KProperty1<HzeState, T>, val defaultValue: T? = null) {

  protected abstract fun reduce(state: T, action: HzeAction): T

  private fun defaultReduce(state: T, action: HzeAction): T {
    val next = reduce(state, action)
    return if (next == undefined) {
      prop.invoke(defaults)
    } else {
      next
    }
  }

  operator fun invoke() = prop to this::defaultReduce

  companion object {
    private val defaults = HzeState()
  }
}