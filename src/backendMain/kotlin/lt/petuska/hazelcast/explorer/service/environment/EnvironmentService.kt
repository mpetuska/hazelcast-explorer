package lt.petuska.hazelcast.explorer.service.environment

import lt.petuska.hazelcast.explorer.configuration.domain.descriptor.environment.*
import lt.petuska.hazelcast.explorer.domain.common.*
import lt.petuska.hazelcast.explorer.domain.environment.*

class EnvironmentService(environments: Set<EnvironmentDescriptor>) {
  private val environmentDTOs = environments.map { it.toDTO() }

  fun getAll(): List<EnvironmentDTO> {
    return environmentDTOs
  }

  fun filter(filter: EnvironmentDTO.() -> Boolean): List<EnvironmentDTO> {
    return environmentDTOs.filter { it.filter() }
  }

  fun find(selector: EnvironmentDTO.() -> Boolean): NullableWrapper<EnvironmentDTO> {
    return environmentDTOs.find { it.selector() }.wrapNullable()
  }

  fun getNames(): List<String> {
    return environmentDTOs.map { it.name }
  }

  fun getDisplayNames(): List<String> {
    return environmentDTOs.map { it.displayName }
  }
}