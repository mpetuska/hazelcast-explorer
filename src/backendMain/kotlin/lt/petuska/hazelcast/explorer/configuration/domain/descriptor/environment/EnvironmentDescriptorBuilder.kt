package lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.*
import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.target.*

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