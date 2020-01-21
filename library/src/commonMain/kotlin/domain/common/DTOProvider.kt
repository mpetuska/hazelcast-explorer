package lt.petuska.hazelcast.explorer.domain.common

interface DTOProvider<T : DTO> {
  fun toDTO(): T
}