package lt.petuska.hazelcast.explorer.serialization

import kotlin.reflect.KClass

public actual data class SerializableKClass<T : Any> actual constructor(
  override val simpleName: String?,
  override val qualifiedName: String?,
) : KClass<T> {
  override fun isInstance(value: Any?): Nothing = throw NotImplementedError("Operation not supported on this shell implementation of KClass. Only simpleName property is supported.")

  override fun equals(other: Any?): Boolean = other is KClass<*> &&
    other.simpleName == simpleName

  override fun hashCode(): Int = toString().hashCode()

  override fun toString(): String {
    val simple = simpleName?.let { "\"$it\"" }
    val qualified = simpleName?.let { "\"$it\"" }
    return "{\"simpleName\": $simple, \"qualifiedName\": $qualified}"
  }
}
