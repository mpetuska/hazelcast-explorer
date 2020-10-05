package lt.petuska.hazelcast.explorer.domain

import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.domain.entity.Map
import lt.petuska.hazelcast.explorer.domain.entity.Topic

@Serializable
public expect class Target {
  public val id: String
  public val name: String
  public val displayName: String
  public val readOnly: Boolean
  public val maps: List<Map>?
  public val topics: List<Topic>?
}
