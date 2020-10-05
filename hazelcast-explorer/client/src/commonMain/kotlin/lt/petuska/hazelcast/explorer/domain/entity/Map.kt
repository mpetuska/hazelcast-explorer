package lt.petuska.hazelcast.explorer.domain.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.serialization.KClassSerializer
import kotlin.reflect.KClass

@Serializable
public data class Map(
  val id: String,
  val name: String,
  val displayName: String,
  val readOnly: Boolean,
  @Serializable(with = KClassSerializer::class)
  val keyType: KClass<out @Contextual Any>,
  @Serializable(with = KClassSerializer::class)
  val valueType: KClass<out @Contextual Any>
)
