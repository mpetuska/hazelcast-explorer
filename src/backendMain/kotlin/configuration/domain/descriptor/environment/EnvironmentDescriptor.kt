package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.*
import lt.petuska.hazelcast.explorer.domain.common.*
import lt.petuska.hazelcast.explorer.domain.environment.*

data class EnvironmentDescriptor(
    override val name: String,
    override val displayName: String = name,
    override val readOnly: Boolean = false,
    val local: Boolean = false,
    val production: Boolean = false,
    val targets: Set<TargetDescriptor>
) : Descriptor, DTOProvider<EnvironmentDTO> {
  override fun toDTO() = EnvironmentDTO(
      name,
      displayName,
      readOnly,
      local,
      production,
      targets.map { it.toDTO() }
  )
}