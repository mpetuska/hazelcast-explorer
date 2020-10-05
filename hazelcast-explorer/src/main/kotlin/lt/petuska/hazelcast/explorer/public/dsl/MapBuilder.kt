package lt.petuska.hazelcast.explorer.public.dsl

import lt.petuska.hazelcast.explorer.domain.entity.Map
import kotlin.reflect.KClass

@HzeDsl
public fun TargetBuilder.map(
  name: String,
  keyType: KClass<*>,
  valueType: KClass<*>,
  displayName: String = name,
  readOnly: Boolean = this.readOnly
): Map = Map("$id[$name]", name, displayName, readOnly, keyType, valueType).also { addMap(it) }
