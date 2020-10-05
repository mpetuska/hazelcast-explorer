package lt.petuska.hazelcast.explorer.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.reflect.KClass

@Serializer(forClass = KClass::class)
internal object KClassSerializer : KSerializer<KClass<out Any>> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("KClass", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: KClass<out Any>) {
    encoder.encodeString("${value.simpleName ?: "UNKNOWN"}|${value.simpleName ?: "UNKNOWN"}")
  }

  override fun deserialize(decoder: Decoder): KClass<out Any> {
    val (simpleName, qualifiedName) = decoder.decodeString().split("|")
    return SerializableKClass(simpleName, qualifiedName)
  }
}
