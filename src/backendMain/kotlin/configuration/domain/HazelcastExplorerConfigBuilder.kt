package lt.petuska.hazelcast.explorer.configuration.domain

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.*


class HazelcastExplorerConfigBuilder(
    val title: String = "Hazelcast Explorer"
) {

  private val environments = mutableSetOf<EnvironmentDescriptor>()

  fun addEnvironment(environment: EnvironmentDescriptor) = environments.add(environment)

  fun build() = HazelcastExplorerConfig(title, environments)
}