package lt.petuska.hazelcast.explorer.domain

import com.hazelcast.core.HazelcastInstance
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import lt.petuska.hazelcast.explorer.domain.entity.Map
import lt.petuska.hazelcast.explorer.domain.entity.Topic

public typealias HazelcastClient = HazelcastInstance

@Serializable
public actual data class Target(
  public actual val id: String,
  public actual val name: String,
  public actual val displayName: String,
  public actual val readOnly: Boolean,
  public actual val maps: List<Map>?,
  public actual val topics: List<Topic>?,
  @Transient
  public val client: (() -> HazelcastClient)? = null
)
