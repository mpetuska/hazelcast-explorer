package lt.petuska.hazelcast.explorer.dsl

import lt.petuska.hazelcast.explorer.domain.Environment
import lt.petuska.hazelcast.explorer.domain.Target

public class EnvironmentBuilder(
  public val name: String,
  public val displayName: String,
  public val readOnly: Boolean
) {
  public val id: String = "[$name]"
  private val targets = mutableListOf<Target>()

  public fun addTarget(target: Target): Boolean = targets.add(target)

  public fun build(): Environment =
    Environment(id, name, displayName, readOnly, targets)
}

@HzeDsl
public fun HzeConfigBuilder.environment(
  name: String,
  displayName: String = name,
  readOnly: Boolean = false,
  builder: EnvironmentBuilder.() -> Unit
): Environment {
  val factory = EnvironmentBuilder(name, displayName, readOnly)
  factory.builder()
  return factory.build().also { addEnvironment(it) }
}
