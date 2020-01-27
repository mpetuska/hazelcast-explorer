package lt.petuska.hazelcast.explorer.domain.environment.target.entity

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.serialization.KClassSerializer
import kotlin.reflect.KClass

@Serializable
data class Map(
  val id: String,
  val name: String,
  val displayName: String,
  val readOnly: Boolean,
  @Serializable(with = KClassSerializer::class)
  val keyType: KClass<out @ContextualSerialization Any>,
  @Serializable(with = KClassSerializer::class)
  val valueType: KClass<out @ContextualSerialization Any>
)