package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target

import com.hazelcast.core.HazelcastInstance
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.Descriptor
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.MapDescriptor
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.TopicDescriptor
import lt.petuska.hazelcast.explorer.domain.common.DTOProvider
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO

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