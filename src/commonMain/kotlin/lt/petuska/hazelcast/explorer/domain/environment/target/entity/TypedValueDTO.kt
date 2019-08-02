package lt.petuska.hazelcast.explorer.domain.environment.target.entity

import kotlinx.serialization.*
import lt.petuska.hazelcast.explorer.domain.common.*

@Serializable
data class TypedValueDTO(
  val typeName: String,
  val valueAsJson: String
) : DTO