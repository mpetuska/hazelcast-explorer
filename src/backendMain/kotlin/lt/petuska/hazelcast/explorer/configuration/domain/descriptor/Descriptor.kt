package lt.petuska.hazelcast.explorer.configuration.domain.descriptor

interface Descriptor {
  val name: String
  val displayName: String
  val readOnly: Boolean
}