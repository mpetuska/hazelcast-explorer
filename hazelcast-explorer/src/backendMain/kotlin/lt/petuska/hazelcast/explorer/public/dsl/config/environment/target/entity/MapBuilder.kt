package lt.petuska.hazelcast.explorer.public.dsl.config.environment.target.entity

import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.public.dsl.HzeDsl
import lt.petuska.hazelcast.explorer.public.dsl.config.environment.target.TargetBuilder
import kotlin.reflect.KClass

@HzeDsl
fun TargetBuilder.map(
  name: String,
  keyType: KClass<*>,
  valueType: KClass<*>,
  displayName: String = name,
  readOnly: Boolean = this.readOnly
) = addMap(Map("$id[$name]", name, displayName, readOnly, keyType, valueType))