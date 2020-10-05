package lt.petuska.hazelcast.explorer.util

import lt.petuska.hazelcast.explorer.domain.enumerator.BType

internal external fun encodeURIComponent(str: String): String
internal external fun encodeURI(str: String): String
internal external fun decodeURIComponent(str: String): String
internal external fun decodeURI(str: String): String

internal fun <T> T.ifUndefined(replacement: T) = if (this == undefined) replacement else this
internal fun <T> T?.ifNullOrUndefined(replacement: T) = this ?: if (this == undefined) replacement else this
internal fun <T> T?.isNullOrUndefined() = this == null || this == undefined

internal fun Int?.bType() = when {
  this in 200..299 -> BType.SUCCESS
  this in 400..499 -> BType.WARNING
  this ?: 0 >= 500 -> BType.DANGER
  else -> BType.PRIMARY
}

@Suppress("UnsafeCastFromDynamic")
internal inline fun <T> jsObject(init: T.() -> Unit): T {
  val o = js("{}")
  init(o)
  return o
}

internal external fun require(module: String): dynamic
