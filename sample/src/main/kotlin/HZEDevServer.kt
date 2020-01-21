package lt.petuska.hazelcast.explorer

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import lt.petuska.hazelcast.explorer.dsl.hazelcastExplorerConfig
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.environment
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.entity.map
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.entity.topic
import lt.petuska.hazelcast.explorer.dsl.hzeConfig.descriptor.target.target
import lt.petuska.hazelcast.explorer.sis.sisHzeConfig
import org.slf4j.event.Level

fun Application.devServer() {
  install(CallLogging) {
    level = Level.INFO
  }
  println("=========================================================================================================")
//  hazelcastExplorer(buildStubConfig(false))
  hazelcastExplorer(sisHzeConfig)
}

fun buildStubConfig(startNodes: Boolean = false) = hazelcastExplorerConfig {
  environment("LOCAL", "Local") {
    val gridClient = LocalHazelcastProvider("local_grid").apply { if (startNodes) startNode() }
    target("M", "M Only", gridClient::getClient) {
      map("local_grid_map1", Long::class, String::class)
      map("local_grid_map2", Long::class, String::class)
    }
    val clusterClient = LocalHazelcastProvider("local_cluster").apply { if (startNodes) startNode() }
    target("T", "T Only", clusterClient::getClient) {
      topic("local_cluster_topic1", String::class)
      topic("local_cluster_topic2", String::class)
    }
  }
  environment("TEST", "Test") {
    val gridClient = LocalHazelcastProvider("test_grid").apply { if (startNodes) startNode() }
    target("MT", "M & T", gridClient::getClient) {
      map("test_grid_map1", Long::class, String::class)
      topic("test_grid_topic1", String::class)
    }
    val clusterClient = LocalHazelcastProvider("test_cluster").apply { if (startNodes) startNode() }
    target("NONE", "None", clusterClient::getClient) {
    }
  }
}