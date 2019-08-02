package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.*
import lt.petuska.hazelcast.explorer.domain.common.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import kotlin.reflect.*

data class TopicDescriptor<V : Any>(
    val environment: String,
    val target: String,
    override val name: String,
    override val displayName: String = name,
    override val readOnly: Boolean = false,
    val valueType: KClass<V>
) : Descriptor, DTOProvider<TopicDTO> {
  override fun toDTO() = TopicDTO(
      environment,
      target,
      name,
      displayName,
      readOnly,
      valueType.qualifiedName!!
  )
}