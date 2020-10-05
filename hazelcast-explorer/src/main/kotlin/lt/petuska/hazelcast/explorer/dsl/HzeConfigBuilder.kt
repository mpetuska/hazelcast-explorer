package lt.petuska.hazelcast.explorer.dsl

import lt.petuska.hazelcast.explorer.domain.Environment
import lt.petuska.hazelcast.explorer.domain.HzeConfig

public class HzeConfigBuilder(
  public val title: String
) {
  private val environments = mutableListOf<Environment>()

  public fun addEnvironment(environment: Environment): Boolean = environments.add(environment)

  public fun build(): HzeConfig = HzeConfig(title, environments)
}

@HzeDsl
public fun hazelcastExplorerConfig(
  title: String = "Hazelcast Explorer",
  builder: HzeConfigBuilder.() -> Unit
): HzeConfig {
  val factory = HzeConfigBuilder(title)
  factory.builder()
  return factory.build()
}
