package lt.petuska.hazelcast.explorer.store

import dev.fritz2.binding.action
import dev.fritz2.binding.handledBy
import dev.fritz2.routing.decodeURIComponent
import dev.fritz2.routing.encodeURIComponent
import kotlinx.browser.window
import dev.fritz2.routing.Route as FRoute

internal class Route : FRoute<Selection> {
  companion object {
    private const val envKey = "env"
    private const val targetKey = "tar"
    private const val mapKey = "m"
    private const val topicKey = "t"
    private const val insertedMapKeyKey = "mKey"
    private const val exploreTypeKey = "et"
    private const val httpMethodKey = "hm"

    private const val assignment = "="
    private const val divider = "&"
  }

  override val default: Selection

  init {
    default = unmarshal(window.location.hash.removePrefix("#"))
    action(default) handledBy SelectionStore.handle { _, sel -> sel }
  }

  private fun extractPair(param: String): Pair<String, String> {
    val equalsPos = param.indexOf(assignment)
    return if (equalsPos > 0) {
      val key = param.substring(0, equalsPos)
      val value = decodeURIComponent(param.substring(equalsPos + 1))
      key to value
    } else param to "true"
  }

  override fun marshal(route: Selection): String =
    mapOf(
      envKey to route.environment?.name,
      targetKey to route.target?.name,
      mapKey to route.map?.name,
      topicKey to route.topic?.name,
      insertedMapKeyKey to route.insertedMapKey,
      exploreTypeKey to route.exploreType.name,
      httpMethodKey to route.httpMethod.name,
    ).filterValues { it != null }.map { (key, value) -> "$key$assignment${encodeURIComponent(value!!)}" }
      .joinToString(separator = divider)

  override fun unmarshal(hash: String): Selection {
    val keys = hash.split(divider).filter { s -> s.isNotBlank() }.asReversed().map(::extractPair).toMap()
    return if (keys.isNotEmpty()) {
      Selection().rebuild(
        keys[envKey],
        keys[targetKey],
        keys[mapKey],
        keys[topicKey],
        keys[insertedMapKeyKey],
        keys[exploreTypeKey],
        keys[httpMethodKey],
      )
    } else {
      Selection()
    }
  }
}
