package lt.petuska.hazelcast.explorer.domain.environment.target

import com.hazelcast.core.HazelcastInstance
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Map
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.Topic

@Serializable
actual data class Target(
  actual val id: String,
  actual val name: String,
  actual val displayName: String,
  actual val readOnly: Boolean,
  actual val maps: List<Map>?,
  actual val topics: List<Topic>?,
  @Transient
  val client: (() -> HazelcastInstance)? = null
)