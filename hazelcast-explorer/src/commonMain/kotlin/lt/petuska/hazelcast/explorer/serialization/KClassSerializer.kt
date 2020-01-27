package lt.petuska.hazelcast.explorer.serialization

import io.ktor.utils.io.core.String
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.CompositeDecoder
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.HexConverter
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KVisibility


@Serializer(forClass = KClass::class)
internal object KClassSerializer : KSerializer<KClass<out Any>> {
  override val descriptor: SerialDescriptor = object : SerialClassDescImpl("ShellKClass<*>") {
    init {
      addElement("simpleName")
    }
  }
  
  override fun serialize(encoder: Encoder, obj: KClass<out Any>) {
    val compositeOutput = encoder.beginStructure(descriptor)
    compositeOutput.encodeStringElement(
      descriptor,
      0,
      HexConverter.printHexBinary((obj.simpleName ?: "UNKNOWN").toByteArray())
    )
    compositeOutput.endStructure(descriptor)
  }
  
  override fun deserialize(decoder: Decoder): KClass<out Any> {
    val dec: CompositeDecoder = decoder.beginStructure(descriptor)
    var simpleName: String? = null
    loop@ while (true) {
      when (val i = dec.decodeElementIndex(descriptor)) {
        CompositeDecoder.READ_DONE -> break@loop
        0 -> simpleName = String(HexConverter.parseHexBinary(dec.decodeStringElement(descriptor, i)))
        else -> throw SerializationException("Unknown index $i")
      }
    }
    dec.endStructure(descriptor)
    return object : KClass<Any> {
      override val qualifiedName: String?
        get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
      override val simpleName: String? = simpleName
      
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