package domain.route

import lt.petuska.hazelcast.explorer.domain.common.*
import kotlin.reflect.*

abstract class RouteDef<T : DTO>(parent: RouteDef<*>? = null, relativePath: String, val entityType: KClass<T>? = null) {
  val absolutePath = "${parent ?: ""}/${relativePath.removePrefix("/")}"

  override fun toString() = absolutePath
}