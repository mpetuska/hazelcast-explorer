package lt.petuska.hazelcast.explorer.configuration.domain

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.EnvironmentDescriptor
import lt.petuska.hazelcast.explorer.domain.HzeConfigDTO
import lt.petuska.hazelcast.explorer.domain.common.DTOProvider

data class HzeConfig(
  val title: String = "Hazelcast Explorer",
  val environments: Set<EnvironmentDescriptor>
) : DTOProvider<HzeConfigDTO> {
  override fun toDTO(): HzeConfigDTO {
    return HzeConfigDTO(title, environments.map { it.toDTO() })
  }
}