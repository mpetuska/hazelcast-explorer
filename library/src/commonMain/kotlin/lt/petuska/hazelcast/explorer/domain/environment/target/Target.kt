package lt.petuska.hazelcast.explorer.domain.environment.target

import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic

@Serializable
expect class Target {
  val id: String
  val name: String
  val displayName: String
  val readOnly: Boolean
  val maps: List<Map>?
  val topics: List<Topic>?
}