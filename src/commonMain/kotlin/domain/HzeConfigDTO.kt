package lt.petuska.hazelcast.explorer.domain

import kotlinx.serialization.*
import lt.petuska.hazelcast.explorer.domain.common.*
import lt.petuska.hazelcast.explorer.domain.environment.*

@Serializable
data class HzeConfigDTO(
    val title: String,
    val environments: List<EnvironmentDTO>
) : DTO()