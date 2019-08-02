package lt.petuska.hazelcast.explorer.domain.environment.target.entity

import kotlinx.serialization.*
import lt.petuska.hazelcast.explorer.domain.common.*

@Serializable
data class TypedMapDTO(
    val keyTypeName: String,
    val valueTypeName: String,
    val mapAsJson: Map<String, String>
) : DTO