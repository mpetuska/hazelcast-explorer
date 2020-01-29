package lt.petuska.hazelcast.explorer.public.dsl

import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import kotlin.reflect.KClass

@HzeDsl
fun TargetBuilder.topic(
  name: String,
  valueType: KClass<*>,
  displayName: String = name,
  readOnly: Boolean = this.readOnly
) = addTopic(Topic("$id[$name]", name, displayName, readOnly, valueType))