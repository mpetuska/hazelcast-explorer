package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.*
import lt.petuska.hazelcast.explorer.domain.common.*
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.*
import kotlin.reflect.*

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

fun <K : Any, V : Any> TargetDescriptorBuilder.map(
  name: String,
  keyType: KClass<K>,
  valueType: KClass<V>,
  displayName: String = name,
  readOnly: Boolean = false
) = addMapDescriptor(MapDescriptor(environment, this@map.name, name, displayName, readOnly, keyType, valueType))