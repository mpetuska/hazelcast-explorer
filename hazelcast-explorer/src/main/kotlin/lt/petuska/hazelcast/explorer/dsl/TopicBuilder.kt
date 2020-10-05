package lt.petuska.hazelcast.explorer.dsl

import lt.petuska.hazelcast.explorer.domain.entity.Topic
import kotlin.reflect.KClass

@HzeDsl
public fun TargetBuilder.topic(
  name: String,
  valueType: KClass<*>,
  displayName: String = name,
  readOnly: Boolean = this.readOnly
): Topic = Topic("$id[$name]", name, displayName, readOnly, valueType).also { addTopic(it) }
