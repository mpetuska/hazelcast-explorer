package lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.entity

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.TopicDescriptor
import lt.petuska.hazelcast.explorer.dsl.HzeDsl
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.TargetDescriptorBuilder
import kotlin.reflect.KClass

@HzeDsl
fun <V : Any> TargetDescriptorBuilder.topic(
  name: String,
  valueType: KClass<V>,
  displayName: String = name,
  readOnly: Boolean = false
) = addTopicDescriptor(TopicDescriptor(environment, this@topic.name, name, displayName, readOnly, valueType))