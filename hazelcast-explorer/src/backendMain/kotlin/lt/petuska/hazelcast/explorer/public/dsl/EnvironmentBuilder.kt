package lt.petuska.hazelcast.explorer.public.dsl

import lt.petuska.hazelcast.explorer.domain.environment.Environment
import lt.petuska.hazelcast.explorer.domain.environment.target.Target

class EnvironmentBuilder(
  val name: String,
  val displayName: String,
  val readOnly: Boolean
) {
  val id = "[$name]"
  private val targets = mutableListOf<Target>()
  
  fun addTarget(target: Target) = targets.add(target)
  
  fun build(): Environment =
    Environment(id, name, displayName, readOnly, targets)
}

@HzeDsl
fun HzeConfigBuilder.environment(
  name: String,
  displayName: String = name,
  readOnly: Boolean = false,
  builder: EnvironmentBuilder.() -> Unit
): Environment {
  val factory = EnvironmentBuilder(name, displayName, readOnly)
  factory.builder()
  return factory.build().also { addEnvironment(it) }
}