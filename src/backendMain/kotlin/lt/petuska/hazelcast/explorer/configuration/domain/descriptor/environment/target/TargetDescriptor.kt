package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target

import com.hazelcast.core.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.*
import lt.petuska.hazelcast.explorer.domain.common.*
import lt.petuska.hazelcast.explorer.domain.environment.target.*

data class TargetDescriptor(
  val environment: String,
  override val name: String,
  override val displayName: String = name,
  override val readOnly: Boolean = false,
  val client: () -> HazelcastInstance,
  val maps: Set<MapDescriptor<*, *>>?,
  val topics: Set<TopicDescriptor<*>>?
) : Descriptor, DTOProvider<TargetDTO> {
  override fun toDTO() = TargetDTO(
    environment,
    name,
    displayName,
    readOnly,
    maps?.map { it.toDTO() },
    topics?.map { it.toDTO() }
  )
}

fun EnvironmentDescriptorBuilder.target(
  name: String,
  displayName: String = name,
  client: () -> HazelcastInstance,
  readOnly: Boolean = false,
  builder: TargetDescriptorBuilder.() -> Unit
): TargetDescriptor {
  val factory = TargetDescriptorBuilder(this.name, name, client, displayName, readOnly)
  factory.builder()
  return factory.build().also { addTarget(it) }
}