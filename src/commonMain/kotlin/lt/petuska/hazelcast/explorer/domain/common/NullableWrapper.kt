package lt.petuska.hazelcast.explorer.domain.common

import kotlinx.serialization.Serializable

@Serializable
data class NullableWrapper<T>(val value: T?)

fun <T> T?.wrapNullable() = NullableWrapper<T>(this)