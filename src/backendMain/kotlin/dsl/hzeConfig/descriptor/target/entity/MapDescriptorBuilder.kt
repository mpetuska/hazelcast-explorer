package lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.entity

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.entity.*
import lt.petuska.hazelcast.explorer.dsl.*
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.*
import kotlin.reflect.*

@HzeDsl
fun <K : Any, V : Any> TargetDescriptorBuilder.map(
    name: String,
    keyType: KClass<K>,
    valueType: KClass<V>,
    displayName: String = name,
    readOnly: Boolean = false
) = addMapDescriptor(MapDescriptor(environment, this@map.name, name, displayName, readOnly, keyType, valueType))