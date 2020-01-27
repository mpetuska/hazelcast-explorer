package lt.petuska.hazelcast.explorer

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import lt.petuska.hazelcast.explorer.public.dsl.config.environment.environment
import lt.petuska.hazelcast.explorer.public.dsl.config.environment.target.entity.map
import lt.petuska.hazelcast.explorer.public.dsl.config.environment.target.entity.topic
import lt.petuska.hazelcast.explorer.public.dsl.config.environment.target.target
import lt.petuska.hazelcast.explorer.public.dsl.config.hazelcastExplorerConfig
import org.slf4j.event.Level

fun Application.devServer() {
  install(CallLogging) {
    level = Level.INFO
  }
  println("=========================================================================================================")
  hazelcastExplorer(buildStubConfig(true))
}

fun buildStubConfig(startNodes: Boolean = false) = hazelcastExplorerConfig {
  environment("LOCAL", "Local") {
    val gridClient = LocalHazelcastProvider("local_grid").apply { if (startNodes) startNode() }
    target("M", gridClient::getClient, "M Only") {
      map("local_grid_map1", Long::class, String::class)
      map("local_grid_map2", Long::class, String::class)
    }
    val clusterClient = LocalHazelcastProvider("local_cluster").apply { if (startNodes) startNode() }
    target("T", clusterClient::getClient, "T Only") {
      topic("local_cluster_topic1", String::class)
      topic("local_cluster_topic2", String::class)
    }
  }
  environment("TEST", "Test") {
    val gridClient = LocalHazelcastProvider("test_grid").apply { if (startNodes) startNode() }
    target("MT", gridClient::getClient, "M & T") {
      map("test_grid_map1", Long::class, String::class)
      topic("test_grid_topic1", String::class)
    }
    val clusterClient = LocalHazelcastProvider("test_cluster").apply { if (startNodes) startNode() }
    target("NONE", clusterClient::getClient, "None") {
    }
  }
}