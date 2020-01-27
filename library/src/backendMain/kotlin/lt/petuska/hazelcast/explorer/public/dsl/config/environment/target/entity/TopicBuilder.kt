package lt.petuska.hazelcast.explorer.public.dsl.config.environment.target.entity

import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic
import lt.petuska.hazelcast.explorer.public.dsl.HzeDsl
import lt.petuska.hazelcast.explorer.public.dsl.config.environment.target.TargetBuilder
import kotlin.reflect.KClass

@HzeDsl
fun TargetBuilder.topic(
  name: String,
  valueType: KClass<*>,
  displayName: String = name,
  readOnly: Boolean = this.readOnly
) = addTopic(Topic("$id[$name]", name, displayName, readOnly, valueType))