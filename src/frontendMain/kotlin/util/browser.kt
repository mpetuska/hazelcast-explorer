package lt.petuska.hazelcast.explorer.util

import io.ktor.http.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*

external fun encodeURIComponent(str: String): String
external fun encodeURI(str: String): String
external fun decodeURIComponent(str: String): String
external fun decodeURI(str: String): String

fun <T> T.ifUndefined(replacement: T) = if (this == undefined) replacement else this
fun <T> T?.ifNullOrUndefined(replacement: T) = this ?: if (this == undefined) replacement else this
fun <T> T?.isNullOrUndefined() = this == undefined || this == null

fun HttpStatusCode?.bType() = when {
  this?.value in 200..299 -> BType.SUCCESS
  this?.value in 400..499 -> BType.WARNING
  this?.value ?: 0 >= 500 -> BType.DANGER
  else -> BType.PRIMARY
}