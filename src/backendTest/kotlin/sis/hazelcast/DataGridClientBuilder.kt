package tv.sis.tools.grid.explorer.hazelcast

import com.hazelcast.client.HazelcastClient
import com.hazelcast.core.HazelcastInstance
import tv.sis.datagrid.client.configuration.HazelcastClientConfigBuilder
import tv.sis.datagrid.common.config.PortableConfiguration
import tv.sis.datagrid.common.config.SerialisationConfigBuilder
import tv.sis.sismodel.constants.SDMaps
import tv.sis.sismodel.constants.SDTopics

object DataGridClientBuilder : HazelcastClientBuilder {
  override fun getTopicValueMapping() = SDTopics.values().map { it.topicName to it.valueType }.toMap()
  
  override fun getMapKeyValueMapping() = SDMaps.values().map { it.mapName to Pair(it.keyType, it.valueType) }.toMap()
  
  private val serializationConfigBuilder = SerialisationConfigBuilder().apply {
    setPortableVersion(1)
    setPortableConfiguration(PortableConfiguration())
  }
  
  override fun build(environment: String): HazelcastInstance {
    val config = HazelcastClientConfigBuilder().buildClientConfig(
      serializationConfigBuilder,
      mutableListOf("racing"),
      environment
    )
    return HazelcastClient.newHazelcastClient(config)
  }
}