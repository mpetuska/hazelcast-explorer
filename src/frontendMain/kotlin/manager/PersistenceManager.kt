package lt.petuska.hazelcast.explorer.manager

import lt.petuska.hazelcast.explorer.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import lt.petuska.hazelcast.explorer.util.*

abstract class PersistenceManager(private val props: List<PersistentProperty>) {
  init {
    init()
  }

  private fun init() = setupSubscriptions()

  protected open fun setupSubscriptions(delay: Long = 50) {
    delay(delay) {
      loadPersistedState()
      store.subscribe { persistState() }
    }
  }

  protected open fun shouldLoad(): Boolean = true
  protected abstract fun load(prop: PersistentProperty): String?
  protected abstract fun save(key: String, value: String?)

  protected fun loadPersistedState() {
    if (shouldLoad()) {
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
      save(key, value)
    }
  }
}