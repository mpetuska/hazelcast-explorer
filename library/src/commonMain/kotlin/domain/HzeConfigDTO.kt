package lt.petuska.hazelcast.explorer.domain

import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.domain.common.DTO
import lt.petuska.hazelcast.explorer.domain.environment.EnvironmentDTO

@Serializable
data class HzeConfigDTO(
  val title: String,
  val environments: List<EnvironmentDTO>
) : DTO()