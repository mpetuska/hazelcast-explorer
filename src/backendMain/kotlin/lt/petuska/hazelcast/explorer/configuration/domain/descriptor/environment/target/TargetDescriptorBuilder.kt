package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target

import com.hazelcast.core.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.*

class TargetDescriptorBuilder(
  val environment: String,
  val name: String,
  val client: () -> HazelcastInstance,
  val displayName: String = name,
  val readOnly: Boolean = false
) : DescriptorBuilder<TargetDescriptor> {

  private val mapDescriptors = mutableSetOf<MapDescriptor<*, *>>()
  private val topicDescriptors = mutableSetOf<TopicDescriptor<*>>()

  fun addMapDescriptor(mapDescriptor: MapDescriptor<*, *>) = mapDescriptors.add(mapDescriptor)
  fun addTopicDescriptor(topicDescriptor: TopicDescriptor<*>) = topicDescriptors.add(topicDescriptor)

  override fun build() = TargetDescriptor(environment, name, displayName, readOnly, client, mapDescriptors, topicDescriptors)
}