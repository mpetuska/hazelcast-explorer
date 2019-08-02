package lt.petuska.hazelcast.explorer.configuration.domain.descriptor

interface DescriptorBuilder<T : Descriptor> {
  fun build(): T
}