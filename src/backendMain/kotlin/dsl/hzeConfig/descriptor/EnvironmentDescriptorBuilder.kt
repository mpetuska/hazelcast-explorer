package lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.DescriptorBuilder
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.EnvironmentDescriptor
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.TargetDescriptor
import lt.petuska.hazelcast.explorer.dsl.HazelcastExplorerConfigBuilder
import lt.petuska.hazelcast.explorer.dsl.HzeDsl

class EnvironmentDescriptorBuilder(
  val name: String,
  val displayName: String = name,
  val readOnly: Boolean = false,
  val local: Boolean = false,
  val production: Boolean = false
) : DescriptorBuilder<EnvironmentDescriptor> {
  
  private val targets = mutableSetOf<TargetDescriptor>()
  
  fun addTarget(target: TargetDescriptor) = targets.add(target)
  
  override fun build(): EnvironmentDescriptor =
    EnvironmentDescriptor(name, displayName, readOnly, local, production, targets)
}

@HzeDsl
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