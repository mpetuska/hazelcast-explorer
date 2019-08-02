package lt.petuska.hazelcast.explorer.domain.enumerator

import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.common.*
import kotlin.reflect.*

enum class Route(parent: Route? = null, val relativePath: String, val entityType: KClass<out DTO>? = null) {
  BASE(null, "/api"),
  META(BASE, "/meta"),
  HZE_CONFIG(META, "/hzeConfig", HzeConfigDTO::class),
  MAP(BASE, "/map"),
  TOPIC(BASE, "/topic");

  val absolutePath = "${parent ?: ""}/${relativePath.removePrefix("/")}"
  override fun toString() = absolutePath
}