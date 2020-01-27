package lt.petuska.hazelcast.explorer.service.entity.map

import lt.petuska.hazelcast.explorer.domain.environment.target.Target
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.service.HazelcastClientService
import kotlin.reflect.full.cast

internal class MapService(
  private val target: Target,
  private val mapDescriptor: Map
) {
  init {
    if (target.maps?.contains<Any>(mapDescriptor) != true) {
      throw IllegalStateException("Target ${target.id} does not contain map [${mapDescriptor.name}]")
    }
  }
  
  val keys
    get() = map.keys.toList()
  
  val values
    get() = map.values.toList()
  
  operator fun get(key: Any): Any? {
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
  
  private fun castKey(key: Any): Any = mapDescriptor.keyType.cast(key)
  private fun castValue(key: Any): Any = mapDescriptor.valueType.cast(key)
  private val map
    get() = HazelcastClientService.getClient(target).getMap<Any, Any>(mapDescriptor.name)
}