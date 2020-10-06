package lt.petuska.hazelcast.explorer.store

import lt.petuska.hazelcast.explorer.config
import lt.petuska.hazelcast.explorer.domain.Environment
import lt.petuska.hazelcast.explorer.domain.Target
import lt.petuska.hazelcast.explorer.domain.entity.Map
import lt.petuska.hazelcast.explorer.domain.entity.Topic
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType
import lt.petuska.hazelcast.explorer.domain.enumerator.HttpMethod

internal data class State(
  val isDarkTheme: Boolean = LocalState.isDarkTheme,
  val selection: Selection = Selection(),
)

internal data class Selection(
  val environment: Environment? = null,
  val target: Target? = null,
  val map: Map? = null,
  val topic: Topic? = null,
  val insertedMapKey: String? = null,
  val exploreType: ExploreType = ExploreType.Default,
  val httpMethod: HttpMethod = HttpMethod.Default,
) {
  fun rebuild(
    environment: String? = null,
    target: String? = null,
    map: String? = null,
    topic: String? = null,
    insertedMapKey: String? = null,
    exploreType: String? = null,
    httpMethod: String? = null,
  ): Selection {
    val env = environment?.let { config.environments.find { it.name == environment } } ?: this.environment
    val tar = env?.targets?.find { it.name == target } ?: env?.targets?.find { it.name == this.target?.name }
    val m = tar?.maps?.find { it.name == map } ?: tar?.maps?.find { it.name == this.map?.name }
    val t = tar?.topics?.find { it.name == topic } ?: tar?.topics?.find { it.name == this.topic?.name }
    val et = exploreType?.let { ExploreType.valueOf(it) } ?: this.exploreType
    val hm = httpMethod?.let { HttpMethod.valueOf(it) } ?: this.httpMethod
    return copy(environment = env, target = tar, map = m, topic = t, insertedMapKey = insertedMapKey, exploreType = et, httpMethod = hm)
  }
}
