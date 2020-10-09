package lt.sample

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import lt.petuska.hazelcast.explorer.dsl.environment
import lt.petuska.hazelcast.explorer.dsl.hazelcastExplorerConfig
import lt.petuska.hazelcast.explorer.dsl.map
import lt.petuska.hazelcast.explorer.dsl.target
import lt.petuska.hazelcast.explorer.dsl.topic
import lt.petuska.hazelcast.explorer.hazelcastExplorer
import org.slf4j.event.Level

fun Application.mainModule() {
  install(CallLogging) {
    level = Level.INFO
  }
  hazelcastExplorer(buildStubConfig(false))
}

fun buildStubConfig(startNodes: Boolean = false) = hazelcastExplorerConfig {
  environment("LOCAL", "Local") {
    val gridClient = EmbeddedHazelcastCluster("local_grid", start = startNodes)
    target("M", gridClient::getClient, "M Only") {
      map("local_grid_map1", Long::class, String::class)
      map("local_grid_map2", Long::class, String::class)
    }
    val clusterClient = EmbeddedHazelcastCluster("local_cluster", start = startNodes)
    target("T", clusterClient::getClient, "T Only") {
      topic("local_cluster_topic1", String::class)
      topic("local_cluster_topic2", String::class)
    }
  }
  environment("TEST", "Test") {
    val gridClient = EmbeddedHazelcastCluster("test_grid", start = startNodes)
    target("MT", gridClient::getClient, "M & T") {
      map("test_grid_map1", Long::class, String::class)
      topic("test_grid_topic1", String::class)
    }
    target("MTR", gridClient::getClient, "M & T (R)", readOnly = true) {
      map("test_grid_mapR", Long::class, String::class)
      topic("test_grid_topicR", String::class)
    }
    val clusterClient = EmbeddedHazelcastCluster("test_cluster", start = startNodes)
    target("NONE", clusterClient::getClient, "None") {
    }
  }
  environment("PROD", "Production", readOnly = true) {
    val gridClient = EmbeddedHazelcastCluster("prod_grid", start = startNodes)
    target("MT", gridClient::getClient, "M & T") {
      map("test_grid_map1", Long::class, String::class)
      topic("test_grid_topic1", String::class)
    }
    val clusterClient = EmbeddedHazelcastCluster("prod_cluster", start = startNodes)
    target("NONE", clusterClient::getClient, "None") {
    }
  }
}
