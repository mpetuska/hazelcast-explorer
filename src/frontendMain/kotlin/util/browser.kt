package lt.petuska.hazelcast.explorer.util

external fun encodeURIComponent(str: String): String
external fun encodeURI(str: String): String
external fun decodeURIComponent(str: String): String
external fun decodeURI(str: String): String

fun <T> T.ifUndefined(replacement: T) = if (this == undefined) replacement else this
fun <T> T?.ifNullOrUndefined(replacement: T) = this ?: if (this == undefined) replacement else this