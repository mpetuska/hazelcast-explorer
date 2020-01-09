package lt.petuska.hazelcast.explorer.service.entity.map

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.*
import lt.petuska.hazelcast.explorer.service.*
import kotlin.reflect.full.*

class MapService<K : Any, V : Any>(private val targetDescriptor: TargetDescriptor, private val mapDescriptor: MapDescriptor<K, V>) {
  init {
    if (targetDescriptor.maps?.contains(mapDescriptor) != true) {
      throw IllegalStateException("Target [${targetDescriptor.environment}][${targetDescriptor.name}] does not contain map [${mapDescriptor.name}]")
    }
  }

  val keys
    get() = map.keys.toList()

  val values
    get() = map.values.toList()

  operator fun get(key: Any): V? {
    val castKey = castKey(key)
    return map[castKey]
  }

  operator fun set(key: Any, value: Any) {
    val castKey = castKey(key)
    val castValue = castValue(value)
    map.set(castKey, castValue)
  }

  fun delete(key: Any) {
    val castKey = castKey(key)
    map.delete(castKey)
  }

  fun create(key: Any, value: Any) = set(key, value)

  fun replace(key: Any, value: Any) {
    val castKey = castKey(key)
    val castValue = castValue(value)
    map.put(castKey, castValue)
  }

  private fun castKey(key: Any): K = mapDescriptor.keyType.cast(key)
  private fun castValue(key: Any): V = mapDescriptor.valueType.cast(key)
  private val map
    get() = HazelcastClientService.getClient(targetDescriptor).getMap<K, V>(mapDescriptor.name)

  companion object {
    fun instanceTag(mapDescriptor: MapDescriptor<*, *>) = "${mapDescriptor.environment}-${mapDescriptor.target}-${mapDescriptor.name}"
  }
}