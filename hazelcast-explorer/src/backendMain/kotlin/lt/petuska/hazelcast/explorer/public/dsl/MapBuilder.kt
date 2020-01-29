package lt.petuska.hazelcast.explorer.public.dsl

import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import kotlin.reflect.KClass

@HzeDsl
fun TargetBuilder.map(
  name: String,
  keyType: KClass<*>,
  valueType: KClass<*>,
  displayName: String = name,
  readOnly: Boolean = this.readOnly
) = addMap(Map("$id[$name]", name, displayName, readOnly, keyType, valueType))