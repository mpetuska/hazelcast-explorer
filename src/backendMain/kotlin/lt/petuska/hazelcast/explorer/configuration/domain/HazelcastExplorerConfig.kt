package lt.petuska.hazelcast.explorer.configuration.domain

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.*
import lt.petuska.hazelcast.explorer.domain.*
import lt.petuska.hazelcast.explorer.domain.common.*

data class HazelcastExplorerConfig(
    val title: String = "Hazelcast Explorer",
    val environments: Set<EnvironmentDescriptor>
) : DTOProvider<HazelcastExplorerConfigDTO> {
  override fun toDTO(): HazelcastExplorerConfigDTO {
    return HazelcastExplorerConfigDTO(title, environments.map { it.toDTO() })
  }
}

fun hazelcastExplorerConfig(
    title: String = "Hazelcast Explorer",
    builder: HazelcastExplorerConfigBuilder.() -> Unit
): HazelcastExplorerConfig {
  val factory = HazelcastExplorerConfigBuilder(title)
  factory.builder()
  return factory.build()
}