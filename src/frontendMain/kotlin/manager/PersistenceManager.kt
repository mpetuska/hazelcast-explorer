package lt.petuska.hazelcast.explorer.manager

import kotlinx.coroutines.*
import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.util.*

abstract class PersistenceManager(private val props: List<PersistentProperty>) {
  init {
    init()
  }

  private fun init() = setupSubscriptions()

  protected open fun setupSubscriptions() {
    delay(100) {
      loadPersistedState()
      store.subscribe { persistState() }
    }
  }

  protected abstract fun load(prop: PersistentProperty): String?
  protected abstract fun save(key: String, value: String)

  protected fun loadPersistedState() {
    promise {
      delay(10)
      props.map { it to load(it) }.forEach {
        val prop = it.first
        val value = it.second
        prop.updater.invoke(store.getState(), value)?.let { action -> store.dispatch(action) }
      }
    }
  }

  private fun persistState() {
    val state = store.getState()
    props.map { it to it.selector(state) }.forEach {
      val key = it.first.key
      val value = it.second
      value?.let { v ->
        save(key, v)
      }
    }
  }
}