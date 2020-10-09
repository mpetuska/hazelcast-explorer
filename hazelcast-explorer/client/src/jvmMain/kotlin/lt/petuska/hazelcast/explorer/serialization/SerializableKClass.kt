package lt.petuska.hazelcast.explorer.serialization

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KVisibility

public actual data class SerializableKClass<T : Any> actual constructor(override val simpleName: String?, override val qualifiedName: String?) : KClass<T> {
  override fun isInstance(value: Any?): Boolean =
    throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override fun equals(other: Any?): Boolean = other is KClass<*> &&
    other.simpleName == simpleName &&
    other.qualifiedName == qualifiedName

  override fun hashCode(): Int = toString().hashCode()

  override fun toString(): String {
    val simple = simpleName?.let { "\"$it\"" }
    val qualified = simpleName?.let { "\"$it\"" }
    return "{\"simpleName\": $simple, \"qualifiedName\": $qualified}"
  }

  override val annotations: List<Annotation>
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val constructors: Collection<KFunction<T>>
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val isAbstract: Boolean
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val isCompanion: Boolean
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val isData: Boolean
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val isFinal: Boolean
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val isFun: Boolean
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

  override val objectInstance: T?
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val sealedSubclasses: List<KClass<out T>>
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val supertypes: List<KType>
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val typeParameters: List<KTypeParameter>
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override val visibility: KVisibility?
    get() = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")
}
