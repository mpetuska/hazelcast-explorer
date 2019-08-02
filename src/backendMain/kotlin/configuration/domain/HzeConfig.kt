package lt.petuska.hazelcast.explorer.configuration.domain

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.*
import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.common.*

data class HzeConfig(
    val title: String = "Hazelcast Explorer",
    val environments: Set<EnvironmentDescriptor>
) : DTOProvider<HzeConfigDTO> {
  override fun toDTO(): HzeConfigDTO {
    return HzeConfigDTO(title, environments.map { it.toDTO() })
  }
}