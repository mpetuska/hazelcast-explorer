package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.Descriptor
import lt.petuska.hazelcast.explorer.domain.common.DTOProvider
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.MapDTO
import kotlin.reflect.KClass

data class MapDescriptor<K : Any, V : Any>(
  val environment: String,
  val target: String,
  override val name: String,
  override val displayName: String = name,
  override val readOnly: Boolean = false,
  val keyType: KClass<K>,
  val valueType: KClass<V>
) : Descriptor, DTOProvider<MapDTO> {
  override fun toDTO() = MapDTO(
    environment,
    target,
    name,
    displayName,
    readOnly,
    keyType.qualifiedName!!,
    valueType.qualifiedName!!
  )
}