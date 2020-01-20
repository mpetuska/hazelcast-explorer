package tv.sis.tools.grid.explorer.hazelcast

import com.hazelcast.core.HazelcastInstance
import tv.sis.channelautomation.common.capgrid.CapGridClientBuilder
import tv.sis.channelautomation.common.capgrid.util.CapMaps
import tv.sis.datagrid.common.config.PortableConfiguration
import tv.sis.datagrid.common.config.SerialisationConfigBuilder

object CapGridClientBuilder : HazelcastClientBuilder {
  override fun getTopicValueMapping(): Map<String, Class<*>> = mapOf()
  
  override fun getMapKeyValueMapping() = CapMaps.values().map { it.mapName to Pair(it.keyType, it.valueType) }.toMap()
  
  private val serializationConfigBuilder = SerialisationConfigBuilder().apply {
    setPortableVersion(1)
    setPortableConfiguration(PortableConfiguration())
  }
  
  override fun build(environment: String): HazelcastInstance {
    return CapGridClientBuilder.getCapGridInstance(
      false,
      environment,
      serializationConfigBuilder
    )
  }
}