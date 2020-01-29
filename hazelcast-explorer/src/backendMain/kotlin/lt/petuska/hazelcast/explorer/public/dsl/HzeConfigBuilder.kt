package lt.petuska.hazelcast.explorer.public.dsl

import lt.petuska.hazelcast.explorer.domain.HzeConfig
import lt.petuska.hazelcast.explorer.domain.environment.Environment


class HzeConfigBuilder(
  val title: String
) {
  private val environments = mutableListOf<Environment>()
  
  fun addEnvironment(environment: Environment) = environments.add(environment)
  
  fun build() = HzeConfig(title, environments)
}

@HzeDsl
fun hazelcastExplorerConfig(
  title: String = "Hazelcast Explorer",
  builder: HzeConfigBuilder.() -> Unit
): HzeConfig {
  val factory = HzeConfigBuilder(title)
  factory.builder()
  return factory.build()
}