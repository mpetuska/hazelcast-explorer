package lt.sample

import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig
import com.hazelcast.config.Config
import com.hazelcast.config.GroupConfig
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance

class LocalHazelcastProvider(
  private val name: String,
  private val password: String = "${name}_superSecretPassword_$name"
) {
  private var hazelcastNode: HazelcastInstance? = null
  private var hazelcastClient: HazelcastInstance? = null
  
  private fun buildClientConfig() = ClientConfig().apply {
    groupConfig = buildGroupConfig()
  }
  
  private fun buildServerConfig() = Config().apply {
    groupConfig = buildGroupConfig()
  }
  
  private fun buildGroupConfig() = GroupConfig().apply {
    name = this@LocalHazelcastProvider.name
    password = this@LocalHazelcastProvider.password
  }
  
  fun startNode(): HazelcastInstance = hazelcastNode ?: run {
    //    Hazelcast.newHazelcastInstance(buildServerConfig()).cluster.shutdown()
    hazelcastNode = Hazelcast.newHazelcastInstance(buildServerConfig())
    hazelcastNode!!
  }
  
  fun getClient(): HazelcastInstance = hazelcastClient ?: run {
    hazelcastClient = HazelcastClient.newHazelcastClient(buildClientConfig())
    hazelcastClient!!
  }
}