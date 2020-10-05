package lt.petuska.hazelcast.explorer.store

import lt.petuska.hazelcast.explorer.config
import lt.petuska.hazelcast.explorer.domain.Environment
import lt.petuska.hazelcast.explorer.domain.Target
import lt.petuska.hazelcast.explorer.domain.entity.Map
import lt.petuska.hazelcast.explorer.domain.entity.Topic
import lt.petuska.hazelcast.explorer.domain.enumerator.ExploreType

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
  val exploreType: ExploreType = ExploreType.MAP,
) {
  fun rebuild(
    environment: String? = null,
    target: String? = null,
    map: String? = null,
    topic: String? = null,
    insertedMapKey: String? = null,
    exploreType: String? = null,
  ): Selection {
    val env = environment?.let { config.environments.find { it.id == environment } } ?: this.environment
    val tar = env?.targets?.find { it.id == target } ?: env?.targets?.find { it.id == this.target?.id }
    val m = tar?.maps?.find { it.id == map } ?: tar?.maps?.find { it.id == this.map?.id }
    val t = tar?.topics?.find { it.id == topic } ?: tar?.topics?.find { it.id == this.topic?.id }
    val et = exploreType?.let { ExploreType.valueOf(it) } ?: this.exploreType
    return copy(environment = env, target = tar, map = m, topic = t, insertedMapKey = insertedMapKey, exploreType = et)
  }
}
