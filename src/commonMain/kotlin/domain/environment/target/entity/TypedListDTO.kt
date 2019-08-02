package lt.petuska.hazelcast.explorer.domain.environment.target.entity

import kotlinx.serialization.*
import lt.petuska.hazelcast.explorer.domain.common.*

@Serializable
data class TypedListDTO(
    val typeName: String,
    val valuesAsJson: List<String>
) : DTO()