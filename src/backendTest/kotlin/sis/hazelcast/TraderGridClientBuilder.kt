package tv.sis.tools.grid.explorer.hazelcast

import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig
import com.hazelcast.client.config.ClientNetworkConfig
import com.hazelcast.config.GroupConfig
import com.hazelcast.core.HazelcastInstance
import java.util.logging.Logger

object TraderGridClientBuilder : HazelcastClientBuilder {
  override fun getTopicValueMapping(): Map<String, Class<*>> = mapOf()
  
  override fun getMapKeyValueMapping() =
    TraderGridMaps.values().map { it.mapName to Pair(it.keyType, it.valueType) }.toMap()
  
  override fun build(environment: String): HazelcastInstance {
    val config = ClientConfig().apply clientConfig@{
      groupConfig = GroupConfig("hzTraderGroup_${environment}", "tr4d3r4pp_${environment}")
      networkConfig = ClientNetworkConfig().apply {
        when (environment) {
          "LOCAL" -> repeat(9) { addAddress("localhost:570${it + 1}") }
          "DEV" -> addAddress("worker.test.docker.sis.tv:4103")
          "QA" -> addAddress("10.226.13.254")
          "UAT" -> addAddress("worker.test.docker.sis.tv:7103")
          "PROD" -> addAddress("prod.sistrader.sis.tv")
          else -> Logger.getAnonymousLogger().info("Trader Grid does not exist on ${environment} environment.")
        }
      }
    }
    return HazelcastClient.newHazelcastClient(config)
  }
  
  enum class TraderGridMaps(val mapName: String, val keyType: Class<*>, val valueType: Class<*>) {
    CUSTOMER_PRICING_INFO("customerPricingInfo", Long::class.java, MutableList::class.java)
  }
}