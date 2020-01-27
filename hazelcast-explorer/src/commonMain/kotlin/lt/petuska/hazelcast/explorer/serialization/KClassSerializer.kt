package lt.petuska.hazelcast.explorer.serialization

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.withName
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KVisibility


@Serializer(forClass = KClass::class)
internal object KClassSerializer : KSerializer<KClass<out Any>> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName("KClass")
  
  override fun serialize(encoder: Encoder, obj: KClass<out Any>) {
    encoder.encodeString(obj.simpleName ?: "UNKNOWN")
  }
  
  override fun deserialize(decoder: Decoder): KClass<out Any> {
    return object : KClass<Any> {
      override val simpleName: String? = decoder.decodeString()
  
      override val qualifiedName: String?
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val annotations: List<Annotation>
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val constructors: Collection<KFunction<Any>>
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val isAbstract: Boolean
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val isCompanion: Boolean
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val isData: Boolean
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val isFinal: Boolean
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val isInner: Boolean
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val isOpen: Boolean
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val isSealed: Boolean
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val members: Collection<KCallable<*>>
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val nestedClasses: Collection<KClass<*>>
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val objectInstance: Any?
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val sealedSubclasses: List<KClass<out Any>>
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val supertypes: List<KType>
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val typeParameters: List<KTypeParameter>
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val visibility: KVisibility?
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      
      override fun isInstance(value: Any?): Boolean =
        throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      
      override fun equals(other: Any?): Boolean = other is KClass<*> && other.simpleName == simpleName
      
      override fun hashCode(): Int = simpleName.hashCode()
      
      override fun toString(): String {
        return "{\"simpleName\": \"$simpleName\"}"
      }
    }
  }
}