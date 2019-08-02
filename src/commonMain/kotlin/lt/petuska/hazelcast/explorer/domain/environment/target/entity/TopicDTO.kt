package lt.petuska.hazelcast.explorer.domain.environment.target.entity

import kotlinx.serialization.*
import lt.petuska.hazelcast.explorer.domain.common.*

@Serializable
data class TopicDTO(
  val environment: String,
  val target: String,
  val name: String,
  val displayName: String,
  val readOnly: Boolean,
  val valueType: String
) : DTO