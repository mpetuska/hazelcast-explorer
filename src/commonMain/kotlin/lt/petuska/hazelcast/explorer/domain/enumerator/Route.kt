package lt.petuska.hazelcast.explorer.domain.enumerator

enum class Route(val parent: Route? = null, val relativePath: String) {
  BASE(null, "/api"),
  META(BASE, "/meta"),
  MAP(BASE, "/map"),
  TOPIC(BASE, "/topic");

  val absolutePath = "${parent ?: ""}/${relativePath.removePrefix("/")}"
  override fun toString() = absolutePath
}