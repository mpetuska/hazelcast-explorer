package lt.petuska.hazelcast.explorer.dsl

import lt.petuska.hazelcast.explorer.configuration.domain.HzeConfig
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.EnvironmentDescriptor


class HazelcastExplorerConfigBuilder(
  val title: String = "Hazelcast Explorer"
) {
  
  private val environments = mutableSetOf<EnvironmentDescriptor>()
  
  fun addEnvironment(environment: EnvironmentDescriptor) = environments.add(environment)
  
  fun build() = HzeConfig(title, environments)
}

@HzeDsl
fun hazelcastExplorerConfig(
  title: String = "Hazelcast Explorer",
  builder: HazelcastExplorerConfigBuilder.() -> Unit
): HzeConfig {
  val factory = HazelcastExplorerConfigBuilder(title)
  factory.builder()
  return factory.build()
}