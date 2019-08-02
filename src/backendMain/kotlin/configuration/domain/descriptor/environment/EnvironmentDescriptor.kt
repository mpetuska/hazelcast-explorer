package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment

import lt.petuska.hazelcast.explorer.configuration.domain.*
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

fun HazelcastExplorerConfigBuilder.environment(
    name: String,
    displayName: String = name,
    readOnly: Boolean = false,
    local: Boolean = false,
    production: Boolean = false,
    builder: EnvironmentDescriptorBuilder.() -> Unit
): EnvironmentDescriptor {
  val factory = EnvironmentDescriptorBuilder(name, displayName, readOnly, local, production)
  factory.builder()
  return factory.build().also { addEnvironment(it) }
}